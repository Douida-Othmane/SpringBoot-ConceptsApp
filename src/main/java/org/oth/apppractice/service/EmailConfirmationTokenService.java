package org.oth.apppractice.service;

import lombok.AllArgsConstructor;
import org.oth.apppractice.Entity.EmailConfirmationToken;
import org.oth.apppractice.Repository.EmailConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmailConfirmationTokenService {

    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    public void saveConfirmationToken(EmailConfirmationToken emailConfirmationToken) {
        emailConfirmationTokenRepository.save(emailConfirmationToken);
    }

    public Optional<EmailConfirmationToken> getConfirmationToken(String token) {
        return emailConfirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return emailConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
