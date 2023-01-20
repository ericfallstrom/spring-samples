package com.efall.springdatasample.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponsePage<T> {

    private Long totalItems;

    private Integer size;

    private List<T> items;

    public static <R> ResponsePage<R> of(Page<R> page) {
        return new ResponsePage<R>(
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getContent());
    }
}
