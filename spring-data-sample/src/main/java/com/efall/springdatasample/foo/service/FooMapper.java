package com.efall.springdatasample.foo.service;

import com.efall.springdatasample.foo.domain.Foo;
import com.efall.springdatasample.foo.domain.FooChild;
import com.efall.springdatasample.foo.dto.FooChildDTO;
import com.efall.springdatasample.foo.dto.FooDTO;
import com.efall.springdatasample.foo.dto.SaveFooChildDTO;
import com.efall.springdatasample.foo.dto.SaveFooDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FooMapper {

    Foo fromDto(final FooDTO fooDTO);

    Foo fromDto(final SaveFooDTO saveFooDTO);

    FooChild fromDto(final FooChildDTO fooChildDTO);

    FooChild fromDto(final SaveFooChildDTO saveFooChildDTO);

    FooDTO toDto(final Foo foo);

    FooChildDTO toDto(final FooChild fooChild);
}
