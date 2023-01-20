package com.efall.springauthsample.security.oauth.user;

import lombok.Data;

import java.util.Map;

@Data
public class FacebookOAuth2UserDetails implements OAuth2UserDetails {

    final Map<String, Object> attributes;

    @Override
    public String firstName() {
        return (String)attributes.get("name");
    }

    @Override
    public String lastName() {
        return null;
    }

    @Override
    public String email() {
        return (String)attributes.get("email");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String imageUrl() {
        if(attributes.containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>)attributes.get("name");
            if(pictureObj != null && pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    return (String) dataObj.get("url");
                }
            }
        }

        return null;
    }
}
