package com.efall.springauthsample.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;


@Entity
@Table(name = "user_authority")
@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@NoArgsConstructor
public class UserAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserAuthority(final String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public static UserAuthority user() {
        return new UserAuthority("ROLE_USER");
    }

    public static UserAuthority admin() {
        return new UserAuthority("ROLE_ADMIN");
    }
}
