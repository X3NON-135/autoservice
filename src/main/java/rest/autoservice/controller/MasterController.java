package rest.autoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.master.MasterRequestDto;
import rest.autoservice.dto.master.MasterResponseDto;
import rest.autoservice.dto.order.OrderResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.mapper.impl.OrderMapper;
import rest.autoservice.model.Master;
import rest.autoservice.service.MasterService;
import rest.autoservice.service.SalaryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/masters")
public class MasterController {
    private final MasterService masterService;
    private final OrderMapper orderMapper;
    private final SalaryService salaryService;
    private final RequestDtoMapper<MasterRequestDto, Master> requestMapper;
    private final ResponseDtoMapper<MasterResponseDto, Master> responseMapper;

    public MasterController(MasterService masterService,
                            OrderMapper orderMapper,
                            SalaryService salaryService,
                            RequestDtoMapper<MasterRequestDto, Master> requestMapper,
                            ResponseDtoMapper<MasterResponseDto, Master> responseMapper) {
        this.masterService = masterService;
        this.orderMapper = orderMapper;
        this.salaryService = salaryService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    public MasterResponseDto create(@RequestBody MasterRequestDto requestDto) {
        Master master = masterService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(master);
    }

    @PutMapping("/{id}")
    public MasterResponseDto update(@PathVariable Long id,
                                    @RequestBody MasterRequestDto requestDto) {
        Master master = requestMapper.toModel(requestDto);
        master.setId(id);
        return responseMapper.toDto(master);
    }

    @GetMapping("/{id}/orders")
    public List<OrderResponseDto> getFinishedOrdersById(@PathVariable Long id) {
        return masterService.getCompletedOrdersById(id).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{masterId}/orders/{odrderId}/salary")
    public BigDecimal countMastersSalaryByOrderId(@PathVariable Long masterId,
                                                  @PathVariable Long orderId) {
        return salaryService.getMasterSalaryByMasterIdAndOrderId(masterId, orderId);
    }
}
