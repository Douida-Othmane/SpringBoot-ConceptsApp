package org.oth.apppractice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.oth.apppractice.Entity.EmailConfirmationToken;
import org.oth.apppractice.dto.RegistrationRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final UserService userService;
    private final EmailConfirmationTokenService emailConfirmationTokenService;

    public String register(RegistrationRequestDto request) {
        return userService.registerUser(request);
    }

    @Transactional
    public String confirmToken(String token) {
        EmailConfirmationToken emailConfirmationToken = emailConfirmationTokenService
                .getConfirmationToken(token)
                .orElseThrow(()-> new IllegalArgumentException("Token not found"));

        if(emailConfirmationToken.getConfirmedAt() !=null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = emailConfirmationToken.getExpiredAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        emailConfirmationTokenService.setConfirmedAt(emailConfirmationToken.getToken());
        userService.enableUser(emailConfirmationToken.getUser().getEmail());

        return "confirmed";
    }
}
