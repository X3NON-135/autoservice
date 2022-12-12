package rest.autoservice.controller;

import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.order.OrderRequestDto;
import rest.autoservice.dto.order.OrderResponseDto;
import rest.autoservice.dto.product.ProductRequestDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.mapper.impl.ProductMapper;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;
import rest.autoservice.service.AutoOwnerService;
import rest.autoservice.service.OrderService;
import rest.autoservice.service.ProductService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final AutoOwnerService autoOwnerService;
    private final RequestDtoMapper<OrderRequestDto, Order> requestMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> responseMapper;

    public OrderController(OrderService orderService,
                           ProductMapper productMapper,
                           ProductService productService,
                           AutoOwnerService autoOwnerService,
                           RequestDtoMapper<OrderRequestDto, Order> requestMapper,
                           ResponseDtoMapper<OrderResponseDto, Order> responseMapper) {
        this.orderService = orderService;
        this.productMapper = productMapper;
        this.productService = productService;
        this.autoOwnerService = autoOwnerService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new Order")
    public OrderResponseDto create(@RequestBody OrderRequestDto requestDto) {
        Order order = orderService.save(requestMapper.toModel(requestDto));
        AutoOwner autoOwner = autoOwnerService.getAutoOwnerByAutoId(requestDto.getAutoId());
        autoOwnerService.save(autoOwnerService.addOrder(autoOwner.getId(), order));
        return responseMapper.toDto(order);
    }

    @PostMapping("/{id}/add-products")
    @ApiOperation(value = "add new Product to Order by id")
    public OrderResponseDto addProductsToOrder(@PathVariable Long id,
                                               @RequestBody ProductRequestDto requestDto) {
        Order order = orderService.addProductToOrder(id, productMapper.toModel(requestDto));
        return responseMapper.toDto(order);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update Order by id")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto requestDto) {
        Order order = requestMapper.toModel(requestDto);
        order.setId(id);
        return responseMapper.toDto(orderService.save(order));
    }

    @PutMapping("/{id}/update-status")
    @ApiOperation(value = "change Order's status")
    public OrderResponseDto changeStatus(@PathVariable Long id,
                                         @RequestParam String status) {
        Order updateOrder = orderService.updateStatus(id, status);
        return responseMapper.toDto(orderService.save(updateOrder));
    }

    @GetMapping("/{id}/calculate-price")
    @ApiOperation(value = "calculate total price for Order")
    public OrderResponseDto calculatePriceForOrder(@PathVariable Long id) {
        return responseMapper.toDto(orderService.calculatePriceForOrder(id));
    }

}
