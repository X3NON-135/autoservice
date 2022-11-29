package rest.autoservice.service;

import rest.autoservice.model.AutoOwner;

public interface AutoOwnerService {
    AutoOwner save(AutoOwner autoOwner);

    AutoOwner findById(Long id);
}
