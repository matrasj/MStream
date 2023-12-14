package pl.matrasj.mail.newsletter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAccountInformationPayload {
    private String email;
    private String firstname;
    private String lastname;
}
