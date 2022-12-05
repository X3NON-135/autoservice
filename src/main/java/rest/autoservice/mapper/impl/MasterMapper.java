package rest.autoservice.mapper.impl;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rest.autoservice.dto.master.MasterRequestDto;
import rest.autoservice.dto.master.MasterResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Master;
import rest.autoservice.model.Order;
import rest.autoservice.service.OrderService;

@Component
public class MasterMapper implements RequestDtoMapper<MasterRequestDto, Master>,
        ResponseDtoMapper<MasterResponseDto, Master> {
    private final OrderService orderService;

    public MasterMapper(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Master toModel(MasterRequestDto requestDto) {
        Master master = new Master();
        master.setFullName(requestDto.getFullName());
        master.setFinishedOrders(requestDto.getFinishedOrdersIds().stream()
                .map(orderService::findById)
                .collect(Collectors.toList()));
        return master;
    }

    @Override
    public MasterResponseDto toDto(Master master) {
        MasterResponseDto responseDto = new MasterResponseDto();
        responseDto.setId(master.getId());
        responseDto.setFullName(master.getFullName());
        responseDto.setFinishedOrdersIds(master.getFinishedOrders().stream()
                .map(Order::getId)
                .collect(Collectors.toList()));
        return responseDto;
    }
}
