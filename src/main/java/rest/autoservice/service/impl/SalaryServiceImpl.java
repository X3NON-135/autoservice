package rest.autoservice.service.impl;

import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Master;
import rest.autoservice.repository.MasterRepository;
import rest.autoservice.service.DutyService;
import rest.autoservice.service.DutyStatusService;
import rest.autoservice.service.SalaryService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
    private final DutyService dutyService;
    private final DutyStatusService dutyStatusService;

    public SalaryServiceImpl(DutyService dutyService,
                             DutyStatusService dutyStatusService) {
        this.dutyService = dutyService;
        this.dutyStatusService = dutyStatusService;
    }

    @Override
    public BigDecimal getMasterSalaryByMasterIdAndOrderId(Long masterId, Long orderId) {
        List<Duty> duties = dutyService.getDutyByMasterIdAndOrOrderId(masterId, orderId);
        duties.forEach(d -> dutyStatusService.updateDutyStatusById(d.getId(), Duty.PaymentStatus.PAID));
        return duties.stream()
                .map(d -> d.getPrice().multiply(BigDecimal.valueOf(0.04)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
