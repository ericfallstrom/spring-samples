package com.efall.springdatasample.foo.dto;

import com.efall.springdatasample.foo.domain.FooData;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SaveFooDTO {

    private String id;

    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    private FooData data;

    private Set<SaveFooChildDTO> children;
}
