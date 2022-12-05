package rest.autoservice.service;

import rest.autoservice.model.Auto;

public interface AutoService {
    Auto save(Auto auto);

    Auto findById(Long id);
}
