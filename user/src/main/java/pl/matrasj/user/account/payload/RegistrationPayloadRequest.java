package pl.matrasj.user.account.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationPayloadRequest {
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "Password must not be blank")
    private String password;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    private String phoneNumber;
}
