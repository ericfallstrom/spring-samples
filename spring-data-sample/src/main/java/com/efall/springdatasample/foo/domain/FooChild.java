package com.efall.springdatasample.foo.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "foo_child")
@Data
@EqualsAndHashCode(exclude = "foo")
@ToString(exclude = "foo")
@NoArgsConstructor
public class FooChild {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "field")
    private String field;

    @ManyToOne
    @JoinColumn(name = "foo_id")
    private Foo foo;
}
