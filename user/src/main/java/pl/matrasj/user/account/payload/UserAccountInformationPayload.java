package pl.matrasj.user.account.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAccountInformationPayload {
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
}
