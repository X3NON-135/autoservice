package rest.autoservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.order.OrderRequestDto;
import rest.autoservice.dto.order.OrderResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Order;
import rest.autoservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final RequestDtoMapper<OrderRequestDto, Order> requestMapper;
    private final ResponseDtoMapper<OrderResponseDto, Order> responseMapper;

    public OrderController(OrderService orderService,
                           RequestDtoMapper<OrderRequestDto, Order> requestMapper,
                           ResponseDtoMapper<OrderResponseDto, Order> responseMapper) {
        this.orderService = orderService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    public OrderResponseDto create(@RequestBody OrderRequestDto requestDto) {
        Order order = orderService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(order);
    }
}
