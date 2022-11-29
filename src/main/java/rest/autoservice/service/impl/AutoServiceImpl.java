package rest.autoservice.service.impl;

import org.springframework.stereotype.Component;
import rest.autoservice.model.Auto;
import rest.autoservice.repository.AutoRepository;
import rest.autoservice.service.AutoService;

@Component
public class AutoServiceImpl implements AutoService {
    private final AutoRepository autoRepository;

    public AutoServiceImpl(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public Auto findById(Long id) {
        return autoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find auto by id=" + id));
    }
}
