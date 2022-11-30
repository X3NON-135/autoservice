package rest.autoservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.master.MasterRequestDto;
import rest.autoservice.dto.master.MasterResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Master;
import rest.autoservice.service.MasterService;

@RestController
@RequestMapping("/master")
public class MasterController {
    private final MasterService masterService;
    private final RequestDtoMapper<MasterRequestDto, Master> requestMapper;
    private final ResponseDtoMapper<MasterResponseDto, Master> responseMapper;

    public MasterController(MasterService masterService,
                            RequestDtoMapper<MasterRequestDto, Master> requestMapper,
                            ResponseDtoMapper<MasterResponseDto, Master> responseMapper) {
        this.masterService = masterService;
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
    }

    @PostMapping
    public MasterResponseDto create(@RequestBody MasterRequestDto requestDto) {
        Master master = masterService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(master);
    }
}
