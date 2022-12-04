package rest.autoservice.controller;

import java.math.BigDecimal;

import io.swagger.annotations.ApiOperation;
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
import rest.autoservice.model.Order;
import rest.autoservice.service.OrderService;
import rest.autoservice.service.ProductService;
import rest.autoservice.service.StatusService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final StatusService statusService;
    private final RequestDtoMapper<OrderRequestDto, Order> requestMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> responseMapper;

    public OrderController(OrderService orderService,
                           ProductMapper productMapper,
                           ProductService productService,
                           StatusService statusService,
                           RequestDtoMapper<OrderRequestDto, Order> requestMapper,
                           ResponseDtoMapper<OrderResponseDto, Order> responseMapper) {
        this.orderService = orderService;
        this.productMapper = productMapper;
        this.productService = productService;
        this.statusService = statusService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new Order")
    public OrderResponseDto create(@RequestBody OrderRequestDto requestDto) {
        Order order = orderService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(order);
    }

    @PostMapping("/{id}/add-products")
    @ApiOperation(value = "add new Product to Order by id")
    public OrderResponseDto addProductsToOrder(@PathVariable Long id,
                                               @RequestBody ProductRequestDto requestDto) {
        Order order = orderService.findById(id);
        order.getProducts().add(productService.save(productMapper.toModel(requestDto)));
        orderService.save(order);
        return responseMapper.toDto(order);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update Order by id")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto requestDto) {
        Order order = requestMapper.toModel(requestDto);
        order.setId(id);
        return responseMapper.toDto(order);
    }

    @PutMapping("/{id}/update-status")
    @ApiOperation(value = "change Order's status")
    public OrderResponseDto changeStatus(@PathVariable Long id,
                                         @RequestBody String status) {
        Order order = statusService.changeOrderStatus(id, Order.Status.valueOf(status));
        return responseMapper.toDto(orderService.save(order));
    }

    @GetMapping("/{id}/calculate-price")
    @ApiOperation(value = "calculate total price for Order")
    public BigDecimal calculatePriceForOrder(@PathVariable Long id,
                                             @RequestParam Integer discount) {
        return orderService.calculatePriceForOrder(id, discount);
    }

}
