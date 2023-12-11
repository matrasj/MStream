package pl.matrasj.user.authentication;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.matrasj.user.account.UserAccountEntity;
import pl.matrasj.user.account.UserAccountRepository;
import pl.matrasj.user.authentication.exception.InvalidCredentialsException;
import pl.matrasj.user.authentication.payload.AuthenticationPayloadRequest;
import pl.matrasj.user.authentication.payload.AuthenticationPayloadResponse;
import pl.matrasj.user.infrastructure.EntityNotFoundException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFacade {
    UserAccountRepository userAccountRepository;
    AuthenticationManager authenticationManager;
    JwtTokenService jwtTokenService;
    UserAccountService userAccountService;

    public AuthenticationPayloadResponse login(AuthenticationPayloadRequest request) {
        UserAccountEntity userAccount = userAccountRepository.findByEmail(request.getEmail())
                .orElseThrow(EntityNotFoundException::new);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException badCredentialsException) {
            throw new InvalidCredentialsException();
        }

        if (authentication.isAuthenticated()) {
            return AuthenticationPayloadResponse.builder()
                    .jwtToken(jwtTokenService.generateJwtToken(userAccount))
                    .build();
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public Boolean hasPermissionForCourse(String bearerToken) {
        UserDetails userDetails = userAccountService.loadUserByUsername(jwtTokenService.extractUsernameFromJwtToken(bearerToken));
        if (!jwtTokenService.isTokenValid(bearerToken, userDetails)) {
            return false;
        }

        return userDetails.getAuthorities()
                .stream()
                .anyMatch((grantedAuthority) -> Objects.equals(grantedAuthority.getAuthority(), Permission.COURSE_ACCESS.name()));
    }
}
