package rest.autoservice.service;

import java.math.BigDecimal;
import java.util.List;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;

public interface MasterService {
    Master save(Master master);

    Master findById(Long id);

    List<Order> getFinishedOrdersByMasterId(Long id);

    BigDecimal getSalary(Long id);
}
