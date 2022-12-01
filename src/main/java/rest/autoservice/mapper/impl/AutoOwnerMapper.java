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
    private final AutoMapper autoMapper;
    private final OrderMapper orderMapper;

    public AutoOwnerMapper(AutoMapper autoMapper,
                           OrderMapper orderMapper) {
        this.autoMapper = autoMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public AutoOwner toModel(AutoOwnerRequestDto requestDto) {
        AutoOwner autoOwner = new AutoOwner();
        autoOwner.setFullName(requestDto.getFullName());
        return autoOwner;
    }

    @Override
    public AutoOwnerResponseDto toDto(AutoOwner autoOwner) {
        AutoOwnerResponseDto responseDto = new AutoOwnerResponseDto();
        responseDto.setId(autoOwner.getId());
        responseDto.setFullName(autoOwner.getFullName());
        responseDto.setAutos(autoOwner.getAutos().stream()
                .map(autoMapper::toDto)
                .collect(Collectors.toList()));
        responseDto.setOrders(autoOwner.getOrders().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
