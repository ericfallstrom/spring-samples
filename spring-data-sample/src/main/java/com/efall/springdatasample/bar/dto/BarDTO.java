package com.efall.springdatasample.bar.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class BarDTO {

    private String id;

    private String name;

    private Instant createdDate;

    private Instant modifiedDate;

    private String fooId;
}
