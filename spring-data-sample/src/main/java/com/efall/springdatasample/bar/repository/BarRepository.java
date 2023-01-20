package com.efall.springdatasample.bar.repository;

import com.efall.springdatasample.bar.domain.Bar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BarRepository extends JpaRepository<Bar, UUID> {

    Page<Bar> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    Optional<Bar> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    @Query("select count(b) > 0 FROM Bar b where b.name = :name")
    boolean isNameTaken(@Param("name") String name);

    @Query("select count(b) > 0 FROM Bar b where b.name = :name and b.id != :id")
    boolean isNameTaken(@Param("name") String name, @Param("id") UUID id);
}
