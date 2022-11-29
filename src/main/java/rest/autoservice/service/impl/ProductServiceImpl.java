package rest.autoservice.service.impl;

import org.springframework.stereotype.Component;
import rest.autoservice.model.Product;
import rest.autoservice.repository.ProductRepository;
import rest.autoservice.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find product by id=" + id));
    }
}
