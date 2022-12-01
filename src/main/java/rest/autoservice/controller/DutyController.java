package rest.autoservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.duties.DutyRequestDto;
import rest.autoservice.dto.duties.DutyResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Duty;
import rest.autoservice.service.DutyService;

@RestController
@RequestMapping("/duties")
public class DutyController {
    private final DutyService dutyService;
    private final RequestDtoMapper<DutyRequestDto, Duty> requestMapper;
    private final ResponseDtoMapper<DutyResponseDto, Duty> responseMapper;

    public DutyController(DutyService dutyService,
                          RequestDtoMapper<DutyRequestDto, Duty> requestMapper,
                          ResponseDtoMapper<DutyResponseDto, Duty> responseMapper) {
        this.dutyService = dutyService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    public DutyResponseDto create(@RequestBody DutyRequestDto requestDto) {
        Duty duty = dutyService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(duty);
    }

    @PutMapping("/{id}")
    public DutyResponseDto update(@PathVariable Long id,
                                  @RequestBody DutyRequestDto requestDto) {
        Duty duty = requestMapper.toModel(requestDto);
        duty.setId(id);
        return responseMapper.toDto(duty);
    }

    @PutMapping("/{id}/update-status")
    public DutyResponseDto updateStatus(@PathVariable Long id,
                                        @RequestBody String status) {
        Duty duty = dutyService.findById(id);
        duty.setId(id);
        duty.setPaymentStatus(Duty.PaymentStatus.valueOf(status));
        return responseMapper.toDto(dutyService.save(duty));
    }
}
