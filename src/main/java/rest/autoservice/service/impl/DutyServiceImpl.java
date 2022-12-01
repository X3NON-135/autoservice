package rest.autoservice.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Duty;
import rest.autoservice.repository.DutyRepository;
import rest.autoservice.service.DutyService;

@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;

    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    @Override
    public Duty save(Duty duty) {
        return dutyRepository.save(duty);
    }

    @Override
    public Duty findById(Long id) {
        return dutyRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find duty by id=" + id));
    }

    @Override
    public List<Duty> getDutyByMasterIdAndOrOrderId(Long masterId, Long orderId) {
        return dutyRepository.getDutyByMasterIdAndOrOrderId(masterId, orderId);
    }
}
