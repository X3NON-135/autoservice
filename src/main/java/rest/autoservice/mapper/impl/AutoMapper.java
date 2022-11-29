package rest.autoservice.mapper.impl;

import org.springframework.stereotype.Component;
import rest.autoservice.dto.auto.AutoRequestDto;
import rest.autoservice.dto.auto.AutoResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Auto;
import rest.autoservice.service.AutoOwnerService;

@Component
public class AutoMapper implements ResponseDtoMapper<AutoResponseDto, Auto>,
        RequestDtoMapper<AutoRequestDto, Auto> {
    private final AutoOwnerService autoOwnerService;

    public AutoMapper(AutoOwnerService autoOwnerService) {
        this.autoOwnerService = autoOwnerService;
    }

    @Override
    public AutoResponseDto toDto(Auto auto) {
        AutoResponseDto responseDto = new AutoResponseDto();
        responseDto.setId(auto.getId());
        responseDto.setBrand(auto.getBrand());
        responseDto.setModel(auto.getModel());
        responseDto.setNumber(auto.getNumber());
        responseDto.setManufactureDate(auto.getManufactureDate());
        responseDto.setOwnerId(auto.getId());
        return responseDto;
    }

    @Override
    public Auto toModel(AutoRequestDto requestDto) {
        Auto auto = new Auto();
        auto.setBrand(requestDto.getBrand());
        auto.setModel(requestDto.getModel());
        auto.setNumber(requestDto.getNumber());
        auto.setManufactureDate(requestDto.getManufactureDate());
        auto.setOwner(autoOwnerService.findById(requestDto.getOwnerId()));
        return auto;
    }
}
