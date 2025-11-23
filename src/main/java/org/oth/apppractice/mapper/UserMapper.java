package org.oth.apppractice.mapper;

import org.mapstruct.Mapper;
import org.oth.apppractice.Entity.User;
import org.oth.apppractice.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User fromDto(UserDto dto);
    List<UserDto> toDtoList(List<User> users);
}

