package pl.matrasj.user.authentication;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.matrasj.user.authentication.payload.AuthenticationPayloadRequest;
import pl.matrasj.user.authentication.payload.AuthenticationPayloadResponse;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationFacade authenticationFacade;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationPayloadResponse> login(@RequestBody AuthenticationPayloadRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationFacade.login(request));
    }

    @GetMapping("/permission-for-course")
    @PreAuthorize("hasAuthority('COURSE_ACCESS')")
    public ResponseEntity<Boolean> hasPermissionForCourse(@RequestHeader(name = "Authorization") String bearerToken) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationFacade.hasPermission(bearerToken.substring(7), Permission.COURSE_ACCESS.name()));
    }

    @GetMapping("/is-admin")
    public ResponseEntity<Boolean> isAdmin(@RequestHeader(name = "Authorization") String bearerToken) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authenticationFacade.hasPermission(bearerToken.substring(7), String.format("ROLE_%s", Role.ADMIN.name())));
    }
}
