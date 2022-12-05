package rest.autoservice.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.order.OrderResponseDto;
import rest.autoservice.dto.owner.AutoOwnerRequestDto;
import rest.autoservice.dto.owner.AutoOwnerResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.mapper.impl.OrderMapper;
import rest.autoservice.model.AutoOwner;
import rest.autoservice.service.AutoOwnerService;

@RestController
@RequestMapping("/owners")
public class AutoOwnerController {
    private final AutoOwnerService autoOwnerService;
    private final OrderMapper orderMapper;
    private final ResponseDtoMapper<AutoOwnerResponseDto, AutoOwner> responseMapper;
    private final RequestDtoMapper<AutoOwnerRequestDto, AutoOwner> requestMapper;

    public AutoOwnerController(AutoOwnerService autoOwnerService,
                               OrderMapper orderMapper,
                               ResponseDtoMapper<AutoOwnerResponseDto, AutoOwner> responseMapper,
                               RequestDtoMapper<AutoOwnerRequestDto, AutoOwner> requestMapper) {
        this.autoOwnerService = autoOwnerService;
        this.responseMapper = responseMapper;
        this.requestMapper = requestMapper;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @ApiOperation(value = "create new Auto Owner")
    public AutoOwnerResponseDto create(@RequestBody AutoOwnerRequestDto requestDto) {
        AutoOwner autoOwner = autoOwnerService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(autoOwner);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update Auto Owner by id")
    public AutoOwnerResponseDto update(@PathVariable Long id,
                                       @RequestBody AutoOwnerRequestDto requestDto) {
        AutoOwner autoOwner = requestMapper.toModel(requestDto);
        autoOwner.setId(id);
        return responseMapper.toDto(autoOwnerService.save(autoOwner));
    }

    @GetMapping("/{id}/orders")
    @ApiOperation(value = "get Auto Owner's orders by Owner's id")
    public List<OrderResponseDto> getOwnersOrders(@PathVariable Long id) {
        AutoOwner autoOwner = autoOwnerService.findById(id);
        return autoOwner.getOrders().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
