package com.efall.springauthsample.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String id;

    private String username;

    private List<UserAuthorityDTO> authorities;
}
