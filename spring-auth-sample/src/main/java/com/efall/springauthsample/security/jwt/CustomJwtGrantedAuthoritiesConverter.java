package com.efall.springauthsample.security.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        String authClaim = "auth";
        Object authorities = source.getClaim(authClaim);

        if (authorities instanceof String) {
            return Arrays.stream(((String) authorities).split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
