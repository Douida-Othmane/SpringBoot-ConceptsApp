package org.oth.apppractice.mapper;

import org.mapstruct.Mapper;
import org.oth.apppractice.Entity.User;
import org.oth.apppractice.dto.RegistrationRequestDto;
import org.oth.apppractice.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto UsertoUserDto(User user);
    User UserDtotoUser(UserDto userDto);
    User UserFromRegistrationDto(RegistrationRequestDto registrationRequestDto);
    RegistrationRequestDto RegistrationDtoFromUser(User user);
    List<UserDto> toDtoList(List<User> users);
}

