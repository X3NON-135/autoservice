package rest.autoservice.service;

import java.math.BigDecimal;
import rest.autoservice.model.Master;

public interface MasterService {
    Master save(Master master);

    Master findById(Long id);

    BigDecimal getSalary(Long id);
}
