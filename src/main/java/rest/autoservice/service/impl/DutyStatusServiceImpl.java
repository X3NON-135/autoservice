package rest.autoservice.service.impl;

import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.repository.DutyRepository;
import rest.autoservice.service.DutyService;
import rest.autoservice.service.DutyStatusService;

@Service
public class DutyStatusServiceImpl implements DutyStatusService {
    private final DutyService dutyService;
    private final DutyRepository dutyRepository;

    public DutyStatusServiceImpl(DutyService dutyService,
                                 DutyRepository dutyRepository) {
        this.dutyService = dutyService;
        this.dutyRepository = dutyRepository;
    }

    @Override
    public Duty updateDutyStatusById(Long id, Duty.PaymentStatus newStatus) {
        dutyRepository.updateDutyStatusById(id, newStatus);
        return dutyService.findById(id);
    }
}
