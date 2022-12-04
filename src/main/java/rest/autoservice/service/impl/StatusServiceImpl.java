package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.repository.DutyRepository;
import rest.autoservice.service.DutyService;
import rest.autoservice.service.MasterService;
import rest.autoservice.service.OrderService;
import rest.autoservice.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {
    private final DutyService dutyService;
    private final OrderService orderService;
    private final MasterService masterService;
    private final DutyRepository dutyRepository;

    public StatusServiceImpl(DutyService dutyService,
                             OrderService orderService,
                             MasterService masterService,
                             DutyRepository dutyRepository) {
        this.dutyService = dutyService;
        this.orderService = orderService;
        this.masterService = masterService;
        this.dutyRepository = dutyRepository;
    }

    @Override
    public Duty updateDutyStatusById(Long id, Duty.PaymentStatus newStatus) {
        dutyRepository.updateDutyStatusById(id, newStatus);
        return dutyService.findById(id);
    }

    @Override
    public Order changeOrderStatus(Long id, Order.Status status) {
        Order order = orderService.findById(id);
        order.setStatus(status);
        List<Master> masters;
        if (status.equals("COMPLETED")) {
            masters = masterService.getAllMastersByOrderId(id);
            saveChanges(order, masters);
        } else if (status.equals("FAILURE")) {
            masters = getMastersWhoDeniedRepairAfterDiagnostics(order);
            saveChanges(order, masters);
        }
        return order;
    }

    private void saveChanges(Order order, List<Master> masters) {
        order.setCompleteDate(LocalDateTime.now());
        orderService.save(order);
        masters.stream()
                .distinct()
                .forEach(m -> masterService.addFinishedOrder(m.getId(), order));
    }

    private List<Master> getMastersWhoDeniedRepairAfterDiagnostics(Order order) {
        Duty duty = order.getDuties().stream()
                .findFirst()
                .get();
        duty.setPrice(BigDecimal.valueOf(500.00));
        return List.of(duty.getMaster());
    }
}
