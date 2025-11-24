package org.oth.apppractice.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EmailConfirmationToken {
    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public EmailConfirmationToken(String token, LocalDateTime expiredAt, LocalDateTime createdAt, User user) {
        this.token = token;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.user = user;
    }
}
