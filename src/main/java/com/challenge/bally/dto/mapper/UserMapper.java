package com.challenge.bally.dto.mapper;

import com.challenge.bally.dto.UserResponseDto;
import com.challenge.bally.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserResponseDto, User> {
}
