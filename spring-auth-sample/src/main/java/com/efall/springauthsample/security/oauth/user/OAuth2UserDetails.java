package com.efall.springauthsample.security.oauth.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserDetails {

    String firstName();

    String lastName();

    String email();

    String imageUrl();

    static OAuth2UserDetails of(final String registrationId, final Authentication authentication) {
        OAuth2User oAuth2User = ((OAuth2User)authentication.getPrincipal());

        return switch (registrationId.toLowerCase()) {
            case "google" -> new GoogleOAuth2UserDetails(oAuth2User.getAttributes());
            case "facebook" -> new FacebookOAuth2UserDetails(oAuth2User.getAttributes());
            default -> throw new IllegalArgumentException("Sorry! Login with " + registrationId + " is not supported yet.");
        };
    }
}
