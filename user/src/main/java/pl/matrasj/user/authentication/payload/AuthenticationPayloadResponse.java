package pl.matrasj.user.authentication.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthenticationPayloadResponse {
    private String jwtToken;
    private String refreshToken;
}
