package pl.matrasj.user.account.kafka;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RegistrationEventPayload {
    private String firstname;
    private String lastname;
    private String email;
    private String confirmationToken;
    private LocalDateTime expirationTime;
}
