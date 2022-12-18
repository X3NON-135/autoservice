package rest.autoservice.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.repository.MasterRepository;
import rest.autoservice.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {
    private static final BigDecimal PERCENT_FROM_DUTY_PRICE = BigDecimal.valueOf(0.4);
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
    public List<Order> getFinishedOrdersByMasterId(Long id) {
        return masterRepository.getFinishedOrdersByMasterId(id);
    }

    @Override
    public BigDecimal getSalary(Long id) {
        List<Order> finishedOrders = masterRepository.getFinishedOrdersByMasterId(id);
        BigDecimal salary = BigDecimal.valueOf(0);
        for (Order finishedOrder : finishedOrders) {
            salary = salary.add(finishedOrder.getTotalPrice());
        }
        return salary.multiply(PERCENT_FROM_DUTY_PRICE);
    }
}
