package pl.matrasj.user.authentication.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AuthenticationPayloadRequest {
    private String email;
    private String password;
}
