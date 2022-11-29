package rest.autoservice.service.impl;

import org.springframework.stereotype.Component;
import rest.autoservice.model.Duty;
import rest.autoservice.repository.DutyRepository;
import rest.autoservice.service.DutyService;

@Component
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;

    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    @Override
    public Duty findById(Long id) {
        return dutyRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find duty by id=" + id));
    }
}
