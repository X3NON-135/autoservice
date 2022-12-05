package rest.autoservice.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.product.ProductRequestDto;
import rest.autoservice.dto.product.ProductResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Product;
import rest.autoservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final RequestDtoMapper<ProductRequestDto, Product> requestMapper;
    private final ResponseDtoMapper<ProductResponseDto, Product> responseMapper;

    public ProductController(ProductService productService,
                             RequestDtoMapper<ProductRequestDto, Product> requestMapper,
                             ResponseDtoMapper<ProductResponseDto, Product> responseMapper) {
        this.productService = productService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new Product")
    public ProductResponseDto create(@RequestBody ProductRequestDto requestDto) {
        Product product = productService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update Product by id")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = requestMapper.toModel(requestDto);
        product.setId(id);
        return responseMapper.toDto(product);
    }
}
