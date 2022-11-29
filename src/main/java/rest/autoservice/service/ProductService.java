package rest.autoservice.service;

import rest.autoservice.model.Product;

public interface ProductService {
    Product save(Product product);

    Product findById(Long id);
}
