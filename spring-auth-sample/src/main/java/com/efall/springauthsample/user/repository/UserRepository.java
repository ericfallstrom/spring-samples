package com.efall.springauthsample.user.repository;

import com.efall.springauthsample.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findOneByUsername(final String username);

    boolean existsByUsername(final String username);
}
