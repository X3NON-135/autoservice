package rest.autoservice.service.impl;

import org.springframework.stereotype.Service;
import rest.autoservice.model.Auto;
import rest.autoservice.repository.AutoRepository;
import rest.autoservice.service.AutoService;

@Service
public class AutoServiceImpl implements AutoService {
    private final AutoRepository autoRepository;

    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public Auto save(Auto auto) {
        return autoRepository.save(auto);
    }

    @Override
    public Auto findById(Long id) {
        return autoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find auto by id=" + id));
    }
}
