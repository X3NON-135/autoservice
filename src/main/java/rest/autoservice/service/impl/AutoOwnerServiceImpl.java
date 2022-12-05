package rest.autoservice.service.impl;

import org.springframework.stereotype.Service;
import rest.autoservice.model.Auto;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;
import rest.autoservice.repository.AutoOwnerRepository;
import rest.autoservice.service.AutoOwnerService;

@Service
public class AutoOwnerServiceImpl implements AutoOwnerService {
    private final AutoOwnerRepository autoOwnerRepository;

    public AutoOwnerServiceImpl(AutoOwnerRepository autoOwnerRepository) {
        this.autoOwnerRepository = autoOwnerRepository;
    }

    @Override
    public AutoOwner save(AutoOwner autoOwner) {
        return autoOwnerRepository.save(autoOwner);
    }

    @Override
    public AutoOwner findById(Long id) {
        return autoOwnerRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find auto owner by id=" + id));
    }

    @Override
    public AutoOwner addAuto(Long id, Auto auto) {
        AutoOwner autoOwner = findById(id);
        autoOwner.getAutos().add(auto);
        return autoOwner;
    }

    @Override
    public AutoOwner addOrder(Long id, Order order) {
        AutoOwner autoOwner = findById(id);
        autoOwner.getOrders().add(order);
        return autoOwner;
    }

    @Override
    public AutoOwner getAutoOwnerByAutoId(Long id) {
        return autoOwnerRepository.getAutoOwnerByAutoId(id);
    }
}
