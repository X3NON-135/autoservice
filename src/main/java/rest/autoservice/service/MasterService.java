package rest.autoservice.service;

import java.util.List;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;

public interface MasterService {
    Master save(Master master);

    Master findById(Long id);

    List<Order> getCompletedOrdersById(Long id);
}
