package pl.matrasj.user.account.payload;


import lombok.Builder;
import lombok.Getter;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenPayloadResponse;

@Getter
@Builder
public class RegistrationPayloadResponse {
    private String email;
    private ConfirmationTokenPayloadResponse confirmationToken;
}
