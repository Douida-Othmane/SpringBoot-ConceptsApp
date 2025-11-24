package org.oth.apppractice.service;

import lombok.AllArgsConstructor;
import org.oth.apppractice.dto.RegistrationRequestDto;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final UserService userService;

    public void register(RegistrationRequestDto request) {
        userService.registerUser(request);
    }
}
