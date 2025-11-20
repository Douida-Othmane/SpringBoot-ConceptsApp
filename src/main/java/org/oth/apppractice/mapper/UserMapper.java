package org.oth.apppractice.mapper;

import org.mapstruct.Mapper;
import org.oth.apppractice.Entity.User;
import org.oth.apppractice.DTO.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User fromDto(UserDTO dto);
    List<UserDTO> toDtoList(List<User> users);
}

