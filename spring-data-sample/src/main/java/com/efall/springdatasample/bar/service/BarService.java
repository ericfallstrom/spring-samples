package com.efall.springdatasample.bar.service;

import com.efall.springdatasample.bar.domain.Bar;
import com.efall.springdatasample.bar.dto.BarDTO;
import com.efall.springdatasample.bar.dto.SaveBarDTO;
import com.efall.springdatasample.bar.repository.BarRepository;
import com.efall.springdatasample.commons.exception.ConflictException;
import com.efall.springdatasample.commons.exception.NotFoundException;
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
public class BarService {

    private final BarRepository barRepository;

    private final BarMapper barMapper;

    public Optional<BarDTO> findById(final UUID id) {
        log.info("BarService.findById :: id={}", id);

        return barRepository.findByIdAndIsDeleted(id, false).map(barMapper::toDto);
    }

    public Page<BarDTO> findAll(Pageable pageable) {
        log.info("BarService.findAll");

        return barRepository.findAllByIsDeleted(false, pageable).map(barMapper::toDto);
    }

    public BarDTO create(SaveBarDTO saveBarDTO) {
        log.info("BarService.create :: bar={}", saveBarDTO);

        Bar bar = barMapper.fromDto(saveBarDTO);

        // Check if new name is already taken
        if(barRepository.isNameTaken(saveBarDTO.getName())) {
            throw new ConflictException("Bar name already exists :: name=" + saveBarDTO.getName());
        }

        return barMapper.toDto(barRepository.save(bar));
    }

    public BarDTO update(SaveBarDTO saveBarDTO, UUID id) {
        log.info("BarService.update :: bar={}", saveBarDTO);

        // Find existing foo
        Bar existing = barRepository.findById(id).orElseThrow(() -> new NotFoundException("Bar not found with id=" + id));

        // Check if new name is already taken
        if(barRepository.isNameTaken(saveBarDTO.getName(), id)) {
            throw new ConflictException("Bar name already exists :: name=" + saveBarDTO.getName());
        }

        // Set new values
        existing.setFooId(UUID.fromString(saveBarDTO.getFooId()));
        existing.setName(saveBarDTO.getName());

        return barMapper.toDto(barRepository.save(existing));
    }

    public void delete(UUID id) {
        log.info("BarService.delete :: id={}", id);

        Bar bar = barRepository.findById(id).orElseThrow(() -> new NotFoundException("Bar not found with id=" + id));
        bar.setIsDeleted(true);

        barRepository.save(bar);
    }
}
