package rest.autoservice.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.repository.MasterRepository;
import rest.autoservice.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {
    private static final double PERCENT_FROM_DUTY_PRICE = 0.4;
    private final MasterRepository masterRepository;

    public MasterServiceImpl(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Override
    public Master save(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master findById(Long id) {
        return masterRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find master by id=" + id));
    }

    @Override
    public List<Order> getFinishedOrdersById(Long id) {
        return findById(id).getFinishedOrders();
    }

    @Override
    public double getSalary(Long id) {
        Master master = findById(id);
        List<Order> finishedOrders = master.getFinishedOrders();
        double salary = 0.0;
        for (int i = 0; i < finishedOrders.size(); i++) {
            if (finishedOrders.get(i).getStatus() != Order.Status.PAID) {
                salary += finishedOrders.get(i).getTotalPrice();
                finishedOrders.get(i).setStatus(Order.Status.PAID);
            }
        }
        return salary * PERCENT_FROM_DUTY_PRICE;
    }
}
