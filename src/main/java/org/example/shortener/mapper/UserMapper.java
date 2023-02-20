package org.example.shortener.mapper;

import org.example.shortener.data.dto.UserDto;
import org.example.shortener.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);
}
