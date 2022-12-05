package rest.autoservice.mapper.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rest.autoservice.dto.order.OrderRequestDto;
import rest.autoservice.dto.order.OrderResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Duty;
import rest.autoservice.model.Order;
import rest.autoservice.model.Product;
import rest.autoservice.service.AutoService;
import rest.autoservice.service.DutyService;
import rest.autoservice.service.ProductService;

@Component
public class OrderMapper implements RequestDtoMapper<OrderRequestDto, Order>,
        ResponseDtoMapper<OrderResponseDto, Order> {
    private final AutoService autoService;
    private final DutyService dutyService;
    private final ProductService productService;

    public OrderMapper(AutoService autoService,
                       DutyService dutyService,
                       ProductService productService) {
        this.autoService = autoService;
        this.dutyService = dutyService;
        this.productService = productService;
    }

    @Override
    public Order toModel(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setAuto(autoService.findById(requestDto.getAutoId()));
        order.setDescription(requestDto.getDescription());
        order.setAcceptanceDate(requestDto.getAcceptanceDate());
        order.setFinishedDate(requestDto.getCompleteDate());
        order.setStatus(requestDto.getStatus());
        order.setDuties(requestDto.getDutiesIds().stream()
                .map(dutyService::findById)
                .collect(Collectors.toList()));
        order.setProducts(requestDto.getProductsIds().stream()
                .map(productService::findById)
                .collect(Collectors.toList()));
        return order;
    }

    @Override
    public OrderResponseDto toDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setAutoId(order.getAuto().getId());
        responseDto.setDescription(order.getDescription());
        responseDto.setAcceptanceDate(LocalDateTime.from(order.getAcceptanceDate()));
        responseDto.setFinishedDate(LocalDateTime.from(order.getFinishedDate()));
        responseDto.setDutyIds(order.getDuties().stream()
                .map(Duty::getId)
                .collect(Collectors.toList()));
        responseDto.setProductIds(order.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList()));
        responseDto.setStatus(String.valueOf(order.getStatus()));
        responseDto.setTotalPrice(order.getTotalPrice());
        return responseDto;
    }
}
