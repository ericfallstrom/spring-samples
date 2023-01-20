package com.efall.springauthsample.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2AuthorizedClientService implements org.springframework.security.oauth2.client.OAuth2AuthorizedClientService {

    private final Oauth2ResponseHandler oauth2ResponseHandler;

    @Override
    public void saveAuthorizedClient(final OAuth2AuthorizedClient authorizedClient, final Authentication principal) {
        oauth2ResponseHandler.oauthSuccessCallback(authorizedClient, principal);
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(final String clientRegistrationId, final String principalName) {
        return null;
    }

    @Override
    public void removeAuthorizedClient(final String clientRegistrationId, final String principalName) {

    }
}
