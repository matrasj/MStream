package pl.matrasj.user.account.payload;


import lombok.Builder;
import lombok.Getter;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenPayloadRes;

@Getter
@Builder
public class RegistrationPayloadResponse {
    private String username;
    private String email;
    private ConfirmationTokenPayloadRes confirmationToken;
}
