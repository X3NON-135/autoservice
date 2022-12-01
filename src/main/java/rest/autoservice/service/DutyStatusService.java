package rest.autoservice.service;

import rest.autoservice.model.Duty;

public interface DutyStatusService {
    Duty updateDutyStatusById(Long id, Duty.PaymentStatus newStatus);
}
