package com.efall.springdatasample.foo.service;

import com.efall.springdatasample.commons.exception.ConflictException;
import com.efall.springdatasample.commons.exception.NotFoundException;
import com.efall.springdatasample.foo.domain.Foo;
import com.efall.springdatasample.foo.domain.FooChild;
import com.efall.springdatasample.foo.dto.FooDTO;
import com.efall.springdatasample.foo.dto.SaveFooDTO;
import com.efall.springdatasample.foo.repository.FooRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FooService {

    private final FooRepository fooRepository;

    private final FooMapper fooMapper;

    public Optional<FooDTO> findById(final UUID id) {
        log.info("FooService.getById :: id={}", id);

        return fooRepository.findByIdAndIsDeleted(id, false).map(fooMapper::toDto);
    }

    public Page<FooDTO> findAll(Pageable pageable) {
        log.info("FooService.findAll");

        return fooRepository.findAllByIsDeleted(false, pageable).map(fooMapper::toDto);
    }


    public FooDTO create(SaveFooDTO saveFooDTO) {
        log.info("FooService.create :: foo={}", saveFooDTO);

        Foo foo = fooMapper.fromDto(saveFooDTO);
        foo.getChildren().forEach(f -> f.setFoo(foo));

        // Check if new name is already taken
        if(fooRepository.isNameTaken(saveFooDTO.getName())) {
            throw new ConflictException("Foo name already exists :: name=" + saveFooDTO.getName());
        }

        return fooMapper.toDto(fooRepository.save(foo));
    }

    public FooDTO update(SaveFooDTO saveFooDTO, UUID id) {
        log.info("FooService.update :: foo={}", saveFooDTO);

        // Find existing foo
        Foo existing = fooRepository.findById(id).orElseThrow(() -> new NotFoundException("Foo not found with id=" + id));

        // Check if new name is already taken
        if(fooRepository.isNameTaken(saveFooDTO.getName(), id)) {
            throw new ConflictException("Foo name already exists :: name=" + saveFooDTO.getName());
        }

        // Set new values
        existing.setName(saveFooDTO.getName());
        existing.setData(saveFooDTO.getData());
        updateFooChildren(existing, saveFooDTO);

        return fooMapper.toDto(fooRepository.save(existing));
    }

    public void delete(UUID id) {
        log.info("FooService.delete :: id={}", id);

        Foo foo = fooRepository.findById(id).orElseThrow(() -> new NotFoundException("Foo not found with id=" + id));
        foo.setIsDeleted(true);

        fooRepository.save(foo);
    }

    private void updateFooChildren(Foo existing, SaveFooDTO saveFooDTO) {
        // Remove missing
        existing.getChildren().removeIf(f -> saveFooDTO.getChildren().stream()
                .noneMatch(s -> f.getName().equals(s.getName())));

        // Update existing
        existing.getChildren().forEach(f -> saveFooDTO.getChildren().stream()
                .filter(s -> s.getName().equals(f.getName()))
                .forEach(s -> f.setField(s.getField())));

        // Add new
        saveFooDTO.getChildren().stream()
                .filter(s -> existing.getChildren().stream().noneMatch(e -> e.getName().equals(s.getName())))
                .forEach(s -> {
                    FooChild fooChild = fooMapper.fromDto(s);
                    fooChild.setFoo(existing);
                    existing.getChildren().add(fooChild);
                });
    }
}
