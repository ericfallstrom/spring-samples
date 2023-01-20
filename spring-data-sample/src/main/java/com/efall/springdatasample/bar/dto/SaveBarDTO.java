package com.efall.springdatasample.bar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveBarDTO {

    @NotNull
    @Size(min = 0, max = 256)
    private String name;

    @NotNull
    private String fooId;
}
