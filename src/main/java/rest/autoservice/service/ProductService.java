package rest.autoservice.service;

import rest.autoservice.model.Product;

public interface ProductService {
    Product findById(Long id);
}
