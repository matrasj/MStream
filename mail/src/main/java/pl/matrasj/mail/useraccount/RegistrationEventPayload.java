package pl.matrasj.mail.useraccount;

import lombok.*;

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
