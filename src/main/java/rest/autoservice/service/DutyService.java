package rest.autoservice.service;

import java.util.List;
import rest.autoservice.model.Duty;

public interface DutyService {
    Duty save(Duty duty);

    Duty findById(Long id);

    List<Duty> getDutyByMasterIdAndOrOrderId(Long masterId, Long orderId);
}
