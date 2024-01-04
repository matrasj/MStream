package pl.matrasj.user.account;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.matrasj.user.account.mapper.UserAccountInformationMapper;
import pl.matrasj.user.account.payload.RegistrationPayloadRequest;
import pl.matrasj.user.account.payload.RegistrationPayloadResponse;
import pl.matrasj.user.account.payload.UserAccountInformationPayload;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenFacade;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user-account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
    UserAccountFacade userAccountFacade;
    ConfirmationTokenFacade confirmationTokenFacade;
    FileSaver fileSaver;
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

    @GetMapping("/assigned-for-newsletter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAccountInformationPayload>> getUsersAssignedForNewsletter() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserAccountInformationMapper.toUserAccountInformationPayload(
                                userAccountFacade.getAllUserAccountsAssignedForNewsletter()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserAccountInformationPayload> getUserAccountInformationByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserAccountInformationMapper.toUserAccountInformationPayload(
                        userAccountFacade.findUserAccountByEmail(email)
                ));
    }

    @PostMapping("/avatar")
    public ResponseEntity<UserAccountInformationPayload> changeAvatar(@RequestPart("file") MultipartFile file) {
        try {
            // Generate a unique file name or use the original file name
            String fileName = "avatar_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Save the file to the avatars directory
            fileSaver.saveAvatar(file.getBytes(), fileName);

            // Handle any other logic, e.g., update user profile with the file name

            return ResponseEntity.status(HttpStatus.OK).body(UserAccountInformationPayload.builder().build());
        } catch (IOException e) {
            // Handle the exception (e.g., log it or return an error response)
            e.printStackTrace();
            return ResponseEntity.status(500).body(UserAccountInformationPayload.builder().build());
        }
    }
}
