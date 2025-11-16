package org.oth.apppractice;

import java.time.LocalDate;

public record UserDTO(Long id, String name, String email, Integer age) {
}
