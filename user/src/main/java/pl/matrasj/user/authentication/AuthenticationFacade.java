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
import org.springframework.stereotype.Service;
import pl.matrasj.user.account.UserAccountEntity;
import pl.matrasj.user.account.UserAccountRepository;
import pl.matrasj.user.authentication.exception.InvalidCredentialsException;
import pl.matrasj.user.authentication.jwt.JwtTokenService;
import pl.matrasj.user.authentication.payload.AuthenticationPayloadRequest;
import pl.matrasj.user.authentication.payload.AuthenticationPayloadResponse;
import pl.matrasj.user.shared.EntityNotFoundException;

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

        String jwtToken = jwtTokenService.generateJwtToken(userAccount);
        if (authentication.isAuthenticated()) {
            return AuthenticationPayloadResponse.builder()
                    .jwtToken(jwtToken)
                    .expiresAt(jwtTokenService.extractClaim(jwtToken, Claims::getExpiration))
                    .email(jwtTokenService.extractClaim(jwtToken, Claims::getSubject))
                    .build();
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public Boolean hasPermission(String jwtToken, String permissionName) {
        UserDetails userDetails = userAccountService.loadUserByUsername(jwtTokenService.extractUsernameFromJwtToken(jwtToken));
        if (!jwtTokenService.isTokenValid(jwtToken, userDetails)) {
            return false;
        }

        return userDetails.getAuthorities()
                .stream()
                .anyMatch((grantedAuthority) -> Objects.equals(grantedAuthority.getAuthority(), permissionName));
    }

    public boolean isTokenValidAndIsEmailSame(String jwtToken, String email) {
        if (jwtToken == null || email == null) return false;
        String emailFromToken = jwtTokenService.extractUsernameFromJwtToken(jwtToken);
        if (!Objects.equals(emailFromToken, email)) return false;
        UserDetails userDetails = userAccountService.loadUserByUsername(emailFromToken);
        return jwtTokenService.isTokenValid(jwtToken, userDetails);
    }
}
