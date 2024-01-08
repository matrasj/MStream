package pl.matrasj.user.account.payload;

import lombok.Builder;
import lombok.Getter;
import pl.matrasj.user.authentication.Role;

@Getter
@Builder
public class UserAccountInformationPayload {
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String avatarImg;
    private Role role;
    private Boolean isAssignedForNewsletter;
}
