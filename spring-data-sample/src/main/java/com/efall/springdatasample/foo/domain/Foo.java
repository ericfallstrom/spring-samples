package com.efall.springdatasample.foo.domain;

import com.efall.springdatasample.commons.domain.EntityAudit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

@Entity
@Table(name = "foo")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"children"})
@NoArgsConstructor
@AllArgsConstructor
public class Foo extends EntityAudit {

    @Column(name = "name")
    private String name;

    @Column(name = "data")
    @JdbcTypeCode(SqlTypes.JSON)
    private FooData data;

    @OneToMany(mappedBy = "foo", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<FooChild> children;
}
