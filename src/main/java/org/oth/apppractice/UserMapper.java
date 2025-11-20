package org.oth.apppractice;

import org.oth.apppractice.DTO.UserDTO;
import org.oth.apppractice.Entity.User;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User fromDto(UserDTO dto);
    List<UserDTO> toDtoList(List<User> users);
}
