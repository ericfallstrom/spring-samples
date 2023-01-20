package com.efall.springdatasample.foo.dto;

import com.efall.springdatasample.foo.domain.FooData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class FooDTO {

    private String id;

    private String name;

    private Instant createdDate;

    private Instant modifiedDate;

    private FooData data;

    private Set<FooChildDTO> children;
}
