package pl.matrasj.user.account;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import pl.matrasj.user.account.validators.PasswordValidator;
import pl.matrasj.user.account.exception.InvalidEmailException;
import pl.matrasj.user.account.exception.InvalidPasswordException;
import pl.matrasj.user.account.exception.InvalidUsernameException;
import pl.matrasj.user.account.exception.UserAccountAlreadyExistsException;
import pl.matrasj.user.account.validators.EmailValidator;
import pl.matrasj.user.account.validators.UsernameValidator;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenEntity;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenFactory;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenPayloadRes;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.function.Predicate;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountFacade {
    UserAccountRepository userAccountRepository;
    ConfirmationTokenFactory confirmationTokenFactory;
    ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    public UserAccountPayloadRes registerAccount(UserAccountPayloadReq accountPayloadReq) {
        validateRequest(accountPayloadReq);

        userAccountRepository.findByEmail(accountPayloadReq.getEmail())
                .ifPresent(existingUser -> {throw new UserAccountAlreadyExistsException();});

        final UserAccountEntity createdAccount = userAccountRepository.save(buildEntityFromRequest(accountPayloadReq));
        final ConfirmationTokenEntity createdConfirmationToken = confirmationTokenRepository.save(
                confirmationTokenFactory.createConfirmationToken(createdAccount.getId())
        );

        return UserAccountPayloadRes.builder()
                .email(createdAccount.getEmail())
                .username(createdAccount.getUsername())
                .confirmationToken(
                        ConfirmationTokenPayloadRes.builder()
                                .token(createdConfirmationToken.getToken())
                                .expiresAt(createdConfirmationToken.getExpiresAt())
                                .build()
                ).build();
    }

    private void validateRequest(final UserAccountPayloadReq accountPayloadReq) {
        final Predicate<String> emailValidator = new EmailValidator();
        final Predicate<String> passwordValidator = new PasswordValidator();
        final Predicate<String> usernameValidator = new UsernameValidator();

        if (!emailValidator.test(accountPayloadReq.getEmail())) throw new InvalidEmailException();
        if (!passwordValidator.test(accountPayloadReq.getEmail())) throw new InvalidPasswordException();
        if (!usernameValidator.test(accountPayloadReq.getEmail())) throw new InvalidUsernameException();
    }

    private UserAccountEntity buildEntityFromRequest(final UserAccountPayloadReq req) {
        return UserAccountEntity.builder()
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .phoneNumber(req.getPhoneNumber())
                .username(req.getUsername())
                .email(req.getEmail())
                .createdAt(LocalDateTime.now())
                .removed(false)
                .enabled(false)
                .build();
    }
}
