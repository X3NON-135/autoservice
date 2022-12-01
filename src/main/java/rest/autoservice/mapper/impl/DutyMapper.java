package rest.autoservice.mapper.impl;

import org.springframework.stereotype.Component;
import rest.autoservice.dto.duties.DutyRequestDto;
import rest.autoservice.dto.duties.DutyResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Duty;
import rest.autoservice.service.MasterService;
import rest.autoservice.service.OrderService;

@Component
public class DutyMapper implements RequestDtoMapper<DutyRequestDto, Duty>,
        ResponseDtoMapper<DutyResponseDto, Duty> {
    private final MasterService masterService;
    private final OrderService orderService;

    public DutyMapper(MasterService masterService,
                      OrderService orderService) {
        this.masterService = masterService;
        this.orderService = orderService;
    }

    @Override
    public Duty toModel(DutyRequestDto requestDto) {
        Duty duty = new Duty();
        duty.setMaster(masterService.findById(requestDto.getMasterId()));
        duty.setOrder(orderService.findById(requestDto.getOrderId()));
        duty.setTypeOfDuty(Duty.TypeOfDuty.valueOf(requestDto.getTypeOfDuty()));
        duty.setPrice(requestDto.getPrice());
        return duty;
    }

    @Override
    public DutyResponseDto toDto(Duty duty) {
        DutyResponseDto responseDto = new DutyResponseDto();
        responseDto.setId(duty.getId());
        responseDto.setOrderId(duty.getOrder().getId());
        responseDto.setMasterId(duty.getMaster().getId());
        responseDto.setPaymentStatus(String.valueOf(duty.getPaymentStatus()));
        responseDto.setTypeOfDuty(String.valueOf(duty.getTypeOfDuty()));
        responseDto.setPrice(duty.getPrice());
        return responseDto;
    }
}
