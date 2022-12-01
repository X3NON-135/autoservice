package rest.autoservice.service;

import rest.autoservice.model.Duty;

import java.util.List;

public interface DutyService {
    Duty save(Duty duty);

    Duty findById(Long id);

    List<Duty> getDutyByMasterIdAndOrOrderId(Long masterId, Long orderId);
}
