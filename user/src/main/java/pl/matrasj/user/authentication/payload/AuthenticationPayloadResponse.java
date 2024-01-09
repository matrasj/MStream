package pl.matrasj.user.authentication.payload;

import lombok.*;
import pl.matrasj.user.authentication.Role;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthenticationPayloadResponse {
    private String jwtToken;
    private Date expiresAt;
    private String email;
    private String refreshToken;
    private Role role;
}
