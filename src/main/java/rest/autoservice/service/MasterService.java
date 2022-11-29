package rest.autoservice.service;

import rest.autoservice.model.Master;

public interface MasterService {
    Master save(Master master);

    Master findById(Long id);
}
