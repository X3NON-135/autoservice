package rest.autoservice.service;

import rest.autoservice.model.Duty;

public interface DutyService {
    Duty save(Duty duty);

    Duty findById(Long id);
}
