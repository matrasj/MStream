package pl.matrasj.user.account;


import lombok.Builder;
import lombok.Getter;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenPayloadRes;

@Getter
@Builder
public class UserAccountPayloadRes {
    private String username;
    private String email;
    private ConfirmationTokenPayloadRes confirmationToken;
}
