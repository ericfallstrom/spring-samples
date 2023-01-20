package com.efall.springdatasample.bar.service;

import com.efall.springdatasample.bar.domain.Bar;
import com.efall.springdatasample.bar.dto.BarDTO;
import com.efall.springdatasample.bar.dto.SaveBarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BarMapper {

    Bar fromDto(final BarDTO barDTO);

    Bar fromDto(final SaveBarDTO saveBarDTO);

    BarDTO toDto(final Bar bar);
}
