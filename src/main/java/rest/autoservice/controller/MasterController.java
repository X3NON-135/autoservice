package rest.autoservice.controller;

import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
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
import rest.autoservice.service.OrderService;

@RestController
@RequestMapping("/masters")
public class MasterController {
    private final MasterService masterService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final RequestDtoMapper<MasterRequestDto, Master> requestMapper;
    private final ResponseDtoMapper<MasterResponseDto, Master> responseMapper;

    public MasterController(MasterService masterService,
                            OrderService orderService,
                            OrderMapper orderMapper,
                            RequestDtoMapper<MasterRequestDto, Master> requestMapper,
                            ResponseDtoMapper<MasterResponseDto, Master> responseMapper) {
        this.masterService = masterService;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new Master")
    public MasterResponseDto create(@RequestBody MasterRequestDto requestDto) {
        Master master = masterService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(master);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update Master by id")
    public MasterResponseDto update(@PathVariable Long id,
                                    @RequestBody MasterRequestDto requestDto) {
        Master master = requestMapper.toModel(requestDto);
        master.setId(id);
        return responseMapper.toDto(masterService.save(master));
    }

    @GetMapping("/{id}/orders")
    @ApiOperation(value = "get Master's orders by Master's id")
    public List<OrderResponseDto> getFinishedOrdersById(@PathVariable Long id) {
        return orderService.getFinishedOrdersByMasterId(id).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/salary")
    @ApiOperation(value = "calculate Master's salary per order by id")
    public BigDecimal calculateMastersSalaryById(@PathVariable Long id) {
        return masterService.getSalary(id);
    }
}
