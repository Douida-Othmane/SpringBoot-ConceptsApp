package org.oth.apppractice.registration;

import lombok.AllArgsConstructor;

public record RegistrationRequestDTO(String name, String email, String password) {
}
