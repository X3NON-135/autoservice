package rest.autoservice.mapper.impl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rest.autoservice.dto.order.OrderRequestDto;
import rest.autoservice.dto.order.OrderResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Order;
import rest.autoservice.service.AutoService;

@Component
public class OrderMapper implements RequestDtoMapper<OrderRequestDto, Order>,
        ResponseDtoMapper<OrderResponseDto, Order> {
    private final AutoService autoService;
    private final DutyMapper dutyMapper;
    private final ProductMapper productMapper;

    public OrderMapper(AutoService autoService,
                       DutyMapper dutyMapper,
                       ProductMapper productMapper) {
        this.autoService = autoService;
        this.dutyMapper = dutyMapper;
        this.productMapper = productMapper;
    }

    @Override
    public Order toModel(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setAuto(autoService.findById(requestDto.getAutoId()));
        order.setDescription(requestDto.getDescription());
        order.setAcceptanceDate(requestDto.getAcceptanceDate());
        order.setCompleteDate(requestDto.getCompleteDate());
        return order;
    }

    @Override
    public OrderResponseDto toDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setAutoId(order.getAuto().getId());
        responseDto.setDescription(order.getDescription());
        responseDto.setAcceptanceDate(LocalDateTime.from(order.getAcceptanceDate()));
        responseDto.setCompleteDate(LocalDateTime.from(order.getCompleteDate()));
        responseDto.setDuties(order.getDuties().stream()
                .map(dutyMapper::toDto)
                .collect(Collectors.toList()));
        responseDto.setProducts(order.getProducts().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList()));
        responseDto.setStatus(String.valueOf(order.getStatus()));
        responseDto.setTotalPrice(order.getTotalPrice());
        return responseDto;
    }
}
