package pl.matrasj.user.account;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matrasj.user.account.payload.RegistrationPayloadRequest;
import pl.matrasj.user.account.payload.RegistrationPayloadResponse;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenFacade;

@RestController
@RequestMapping("/api/user-account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
    UserAccountFacade userAccountFacade;
    ConfirmationTokenFacade confirmationTokenFacade;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationPayloadResponse> registerAccount(@RequestBody @Valid RegistrationPayloadRequest registrationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userAccountFacade.registerAccount(registrationRequest));
    }
    
    @GetMapping("/confirmation")
    public ResponseEntity<String> confirmAccount(@RequestParam("token") String token) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(confirmationTokenFacade.confirmAccount(token));
    }
}
