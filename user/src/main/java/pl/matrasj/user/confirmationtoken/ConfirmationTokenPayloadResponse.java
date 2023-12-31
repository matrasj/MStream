package pl.matrasj.user.confirmationtoken;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConfirmationTokenPayloadResponse {
    private String token;
    private LocalDateTime expiresAt;
}
