package com.efall.springdatasample.foo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FooChildDTO {

    private String id;

    private String name;

    private String field;
}
