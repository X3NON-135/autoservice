package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.service.DutyService;
import rest.autoservice.service.DutyStatusService;
import rest.autoservice.service.SalaryService;

@Service
public class SalaryServiceImpl implements SalaryService {
    private static final long PERCENT_FROM_DUTY_PRICE = (long) 0.04;
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
        duties.forEach(duty -> dutyStatusService.updateDutyStatusById(
                duty.getId(), Duty.PaymentStatus.PAID));
        return duties.stream()
                .map(d -> d.getPrice().multiply(BigDecimal.valueOf(PERCENT_FROM_DUTY_PRICE)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
