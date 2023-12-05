package pl.matrasj.user.account.kafka.registration;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RegistrationEventPayload {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String confirmationToken;
    private LocalDateTime expirationTime;
}
