package rest.autoservice.service;

import rest.autoservice.model.Duty;
import rest.autoservice.model.Order;

public interface StatusService {
    Duty updateDutyStatusById(Long id, Duty.PaymentStatus newStatus);

    Order changeOrderStatus(Long id, Order.Status status);
}
