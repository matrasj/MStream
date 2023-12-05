package pl.matrasj.user.confirmationtoken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ConfirmationTokenFactory {
    @Value("${user.confirmation-token.expire-time-in-minutes:15}")
    private int expireTimeInMinutes;
    public ConfirmationTokenEntity createConfirmationToken(Long userAccountId) {
        return ConfirmationTokenEntity.builder()
                .userAccountId(userAccountId)
                .token(UUID.randomUUID().toString())
                .createDate(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(expireTimeInMinutes))
                .build();
    }
}
