package rest.autoservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.autoservice.dto.auto.AutoRequestDto;
import rest.autoservice.dto.auto.AutoResponseDto;
import rest.autoservice.mapper.RequestDtoMapper;
import rest.autoservice.mapper.ResponseDtoMapper;
import rest.autoservice.model.Auto;
import rest.autoservice.service.AutoService;

@RestController
@RequestMapping("/autos")
public class AutoController {
    private final AutoService autoService;
    private final ResponseDtoMapper<AutoResponseDto, Auto> responseMapper;
    private final RequestDtoMapper<AutoRequestDto, Auto> requestMapper;

    public AutoController(AutoService autoService,
                          ResponseDtoMapper<AutoResponseDto, Auto> responseMapper,
                          RequestDtoMapper<AutoRequestDto, Auto> requestMapper) {
        this.autoService = autoService;
        this.responseMapper = responseMapper;
        this.requestMapper = requestMapper;
    }

    @PostMapping
    public AutoResponseDto createAuto(@RequestBody AutoRequestDto requestDto) {
        Auto auto = autoService.save(requestMapper.toModel(requestDto));
        return responseMapper.toDto(auto);
    }

    @PutMapping("/{id}")
    public AutoResponseDto update(@PathVariable Long id,
                                  @RequestBody AutoRequestDto requestDto) {
        Auto auto = requestMapper.toModel(requestDto);
        auto.setId(id);
        return responseMapper.toDto(autoService.save(auto));
    }
}
