package com.efall.springauthsample.user.service;

import com.efall.springauthsample.user.domain.User;
import com.efall.springauthsample.user.dto.UserDTO;
import com.efall.springauthsample.user.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsername(username).orElseThrow();
    }

    public Optional<UserDTO> findById(final UUID id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    public Optional<UserDTO> findByUsername(final String username) {
        return userRepository.findOneByUsername(username).map(userMapper::toDto);
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(User user, @Nullable String password) {
        log.info("Creating new user :: username={}", user.getUsername());
        String username = user.getUsername();

        // Return error if username is taken
        Optional<User> existingUser = userRepository.findOneByUsername(username);
        existingUser.ifPresent(value -> {
            log.error("User with username already exists :: username={}", username);
            throw new RuntimeException("User with username already exists :: username=" + username);
        });

        // Set encoded password
        String encodedPass = password == null ? null : passwordEncoder.encode(password);
        user.setPassword(encodedPass);

        // Save user
        userRepository.save(user);
    }
}
