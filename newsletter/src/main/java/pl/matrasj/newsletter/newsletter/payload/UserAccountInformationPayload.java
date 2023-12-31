package pl.matrasj.newsletter.newsletter.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAccountInformationPayload {
    private String email;
    private String firstname;
    private String lastname;
}
