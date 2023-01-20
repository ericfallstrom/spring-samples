package com.efall.springdatasample.bar.domain;

import com.efall.springdatasample.commons.domain.EntityAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "bar")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Bar extends EntityAudit {

    @Column(name = "name")
    private String name;

    @Column(name = "foo_id")
    private UUID fooId;
}
