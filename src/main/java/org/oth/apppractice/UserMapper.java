package org.oth.apppractice;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User fromDto(UserDTO dto);
    List<UserDTO> toDtoList(List<User> users);
}
