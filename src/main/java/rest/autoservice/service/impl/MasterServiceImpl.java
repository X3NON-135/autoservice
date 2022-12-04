package rest.autoservice.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.repository.MasterRepository;
import rest.autoservice.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {
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
    public List<Order> getCompletedOrdersById(Long id) {
        return masterRepository.getCompletedOrdersById(id);
    }
}
