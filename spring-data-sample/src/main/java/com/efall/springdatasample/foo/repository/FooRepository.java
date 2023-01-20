package com.efall.springdatasample.foo.repository;

import com.efall.springdatasample.foo.domain.Foo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FooRepository extends JpaRepository<Foo, UUID> {

    Page<Foo> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    Optional<Foo> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    @Query("select count(f) > 0 FROM Foo f where f.name = :name")
    boolean isNameTaken(@Param("name") String name);

    @Query("select count(f) > 0 FROM Foo f where f.name = :name and f.id != :id")
    boolean isNameTaken(@Param("name") String name, @Param("id") UUID id);
}
