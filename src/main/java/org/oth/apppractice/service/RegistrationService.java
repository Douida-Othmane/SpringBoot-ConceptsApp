package org.oth.apppractice.service;

import org.oth.apppractice.dto.RegistrationRequestDto;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequestDto request) {
        return "works";
    }
}
