package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.repository.MasterRepository;
import rest.autoservice.service.MasterService;
import rest.autoservice.service.OrderService;

@Service
public class MasterServiceImpl implements MasterService {
    private static final BigDecimal PERCENT_FROM_DUTY_PRICE = BigDecimal.valueOf(0.4);
    private final MasterRepository masterRepository;
    private final OrderService orderService;

    public MasterServiceImpl(MasterRepository masterRepository, OrderService orderService) {
        this.masterRepository = masterRepository;
        this.orderService = orderService;
    }

    @Override
    public Master save(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master findById(Long id) {
        return masterRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find master by id=" + id));
    }

    @Override
    public BigDecimal getSalary(Long id) {
        List<Order> finishedOrders = orderService.getFinishedOrdersByMasterId(id);
        BigDecimal salary = BigDecimal.valueOf(0);
        for (Order finishedOrder : finishedOrders) {
            salary = salary.add(finishedOrder.getTotalPrice());
        }
        return salary.multiply(PERCENT_FROM_DUTY_PRICE);
    }
}
