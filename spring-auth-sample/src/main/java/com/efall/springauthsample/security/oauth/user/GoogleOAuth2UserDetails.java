package com.efall.springauthsample.security.oauth.user;

import lombok.Data;

import java.util.Map;

@Data
public class GoogleOAuth2UserDetails implements OAuth2UserDetails {

    private final Map<String, Object> attributes;

    @Override
    public String firstName() {
        return (String)attributes.get("given_name");
    }

    @Override
    public String lastName() {
        return (String)attributes.get("family_name");
    }

    @Override
    public String email() {
        return (String)attributes.get("email");
    }

    @Override
    public String imageUrl() {
        return (String)attributes.get("picture");
    }
}
