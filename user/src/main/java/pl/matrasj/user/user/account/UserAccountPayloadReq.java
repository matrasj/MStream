package pl.matrasj.user.user.account;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAccountPayloadReq {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
