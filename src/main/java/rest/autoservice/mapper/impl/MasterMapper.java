package rest.autoservice.mapper.impl;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rest.autoservice.dto.master.MasterRequestDto;
import rest.autoservice.dto.master.MasterResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Master;
import rest.autoservice.service.OrderService;

@Component
public class MasterMapper implements RequestDtoMapper<MasterRequestDto, Master>,
        ResponseDtoMapper<MasterResponseDto, Master> {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public MasterMapper(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    public Master toModel(MasterRequestDto requestDto) {
        Master master = new Master();
        master.setFullName(requestDto.getFullName());
        return master;
    }

    @Override
    public MasterResponseDto toDto(Master master) {
        MasterResponseDto responseDto = new MasterResponseDto();
        responseDto.setId(master.getId());
        responseDto.setFullName(master.getFullName());
        responseDto.setFinishedOrders(master.getFinishedOrders().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
