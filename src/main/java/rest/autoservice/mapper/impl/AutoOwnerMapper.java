package rest.autoservice.mapper.impl;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rest.autoservice.dto.owner.AutoOwnerRequestDto;
import rest.autoservice.dto.owner.AutoOwnerResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Auto;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.model.Order;
import rest.autoservice.service.AutoService;
import rest.autoservice.service.OrderService;

@Component
public class AutoOwnerMapper implements RequestDtoMapper<AutoOwnerRequestDto, AutoOwner>,
        ResponseDtoMapper<AutoOwnerResponseDto, AutoOwner> {
    private final AutoService autoService;
    private final OrderService orderService;

    public AutoOwnerMapper(AutoService autoService,
                           OrderService orderService) {
        this.autoService = autoService;
        this.orderService = orderService;
    }

    @Override
    public AutoOwner toModel(AutoOwnerRequestDto requestDto) {
        AutoOwner autoOwner = new AutoOwner();
        autoOwner.setFullName(requestDto.getFullName());
        autoOwner.setAutos(requestDto.getAutoIds().stream()
                .map(autoService::findById)
                .collect(Collectors.toList()));
        autoOwner.setOrders(requestDto.getOrderIds().stream()
                .map(orderService::findById)
                .collect(Collectors.toList()));
        return autoOwner;
    }

    @Override
    public AutoOwnerResponseDto toDto(AutoOwner autoOwner) {
        AutoOwnerResponseDto responseDto = new AutoOwnerResponseDto();
        responseDto.setId(autoOwner.getId());
        responseDto.setFullName(autoOwner.getFullName());
        responseDto.setAutoIds(autoOwner.getAutos().stream()
                .map(Auto::getId)
                .collect(Collectors.toList()));
        responseDto.setOrderIds(autoOwner.getOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
