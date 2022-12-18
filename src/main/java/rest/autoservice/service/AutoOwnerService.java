package rest.autoservice.service;

import java.util.List;
import rest.autoservice.model.Auto;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;

public interface AutoOwnerService {
    AutoOwner save(AutoOwner autoOwner);

    AutoOwner findById(Long id);

    AutoOwner addAuto(Long id, Auto auto);

    List<Order> getOrdersByAutoOwnerId(Long id);
}
