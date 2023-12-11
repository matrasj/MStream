package pl.matrasj.user.authentication.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtTokenPropertiesConfig {
    @Value("${token.jwt-token-expiration-time-minutes}")
    Long jwtTokenExpirationTimeInMinutes;
    @Value("${token.refresh-token-expiration-time-days}")
    Long refreshTokenExpirationTimeInDays;
    @Value("${token.jwt-token-secret-key}")
    String secretKey;
}
