package com.efall.springdatasample.foo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveFooChildDTO {

    private String id;

    @NotNull
    @Size(min = 0, max = 128)
    private String name;

    @NotNull
    @Size(min = 0, max = 128)
    private String field;
}
