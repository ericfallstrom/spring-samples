package com.efall.springauthsample.user.service;

import com.efall.springauthsample.user.domain.User;
import com.efall.springauthsample.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User fromDto(final UserDTO userDTO);

    UserDTO toDto(final User user);
}
