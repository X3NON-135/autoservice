package rest.autoservice.service;

import rest.autoservice.model.Auto;

public interface AutoService {
    Auto findById(Long id);
}