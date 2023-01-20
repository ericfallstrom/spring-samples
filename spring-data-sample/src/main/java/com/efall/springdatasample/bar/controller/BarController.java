package com.efall.springdatasample.bar.controller;

import com.efall.springdatasample.bar.dto.BarDTO;
import com.efall.springdatasample.bar.dto.SaveBarDTO;
import com.efall.springdatasample.bar.service.BarService;
import com.efall.springdatasample.commons.dto.ResponsePage;
import com.efall.springdatasample.commons.exception.BadRequestException;
import com.efall.springdatasample.commons.exception.NotFoundException;
import com.efall.springdatasample.foo.service.FooService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/bar")
@RequiredArgsConstructor
public class BarController {

    private final BarService barService;

    private final FooService fooService;

    @GetMapping("/{id}")
    public BarDTO findOne(@PathVariable UUID id) {
        log.trace("BarController.findOne");

        return barService.findById(id).orElseThrow(() -> new NotFoundException("Bar not found with id=" + id));
    }

    @GetMapping
    public ResponsePage<BarDTO> findAll(Pageable pageable) {
        log.trace("BarController.findAll");

        return ResponsePage.of(barService.findAll(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BarDTO create(@RequestBody @Validated SaveBarDTO saveBarDTO) {
        log.trace("BarController.create");

        fooService.findById(UUID.fromString(saveBarDTO.getFooId()))
                .orElseThrow(() -> new BadRequestException("Foo not found with id=" + saveBarDTO.getFooId()));

        return barService.create(saveBarDTO);
    }

    @PutMapping("/{id}")
    public BarDTO update(@RequestBody @Validated SaveBarDTO saveBarDTO, @PathVariable UUID id) {
        log.trace("BarController.update");

        fooService.findById(UUID.fromString(saveBarDTO.getFooId()))
                .orElseThrow(() -> new BadRequestException("Foo not found with id=" + saveBarDTO.getFooId()));;

        return barService.update(saveBarDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        log.trace("BarController.delete");

        barService.delete(id);
    }
}
