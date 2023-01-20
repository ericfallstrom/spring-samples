package com.efall.springdatasample.foo.controller;

import com.efall.springdatasample.commons.dto.ResponsePage;
import com.efall.springdatasample.commons.exception.NotFoundException;
import com.efall.springdatasample.foo.dto.FooDTO;
import com.efall.springdatasample.foo.dto.SaveFooDTO;
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
@RequestMapping("/api/foo")
@RequiredArgsConstructor
public class FooController {

    private final FooService fooService;

    @GetMapping("/{id}")
    public FooDTO getOne(@PathVariable UUID id) {
        log.trace("FooController.findOne");

        return fooService.findById(id).orElseThrow(() -> new NotFoundException("Foo not found with id=" + id));
    }

    @GetMapping
    public ResponsePage<FooDTO> findAll(Pageable pageable) {
        log.trace("FooController.findAll");

        return ResponsePage.of(fooService.findAll(pageable));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FooDTO create(@RequestBody @Validated SaveFooDTO saveFooDTO) {
        log.trace("FooController.create");

        return fooService.create(saveFooDTO);
    }

    @PutMapping("/{id}")
    public FooDTO update(@RequestBody @Validated SaveFooDTO saveFooDTO, @PathVariable UUID id) {
        log.trace("FooController.update");

        return fooService.update(saveFooDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        log.trace("FooController.delete");

        fooService.delete(id);
    }
}
