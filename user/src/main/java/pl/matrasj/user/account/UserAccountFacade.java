package pl.matrasj.user.account;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.matrasj.user.account.kafka.registration.KafkaRegistrationEventProducer;
import pl.matrasj.user.account.kafka.registration.RegistrationEventPayload;
import pl.matrasj.user.account.payload.RegistrationPayloadRequest;
import pl.matrasj.user.account.payload.RegistrationPayloadResponse;
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
    KafkaRegistrationEventProducer kafkaRegistrationEventProducer;
    @Transactional
    public RegistrationPayloadResponse registerAccount(RegistrationPayloadRequest accountPayloadReq) {
        validateRequest(accountPayloadReq);

        userAccountRepository.findByEmail(accountPayloadReq.getEmail())
                .ifPresent(existingUser -> {throw new UserAccountAlreadyExistsException();});

        final UserAccountEntity createdAccount = userAccountRepository.save(buildEntityFromRequest(accountPayloadReq));
        final ConfirmationTokenEntity createdConfirmationToken = confirmationTokenRepository.save(
                confirmationTokenFactory.createConfirmationToken(createdAccount.getId())
        );

        kafkaRegistrationEventProducer.pushRegistrationEvent(
                buildRegistrationEvent(createdAccount, createdConfirmationToken)
        );

        return RegistrationPayloadResponse.builder()
                .email(createdAccount.getEmail())
                .username(createdAccount.getUsername())
                .confirmationToken(
                        ConfirmationTokenPayloadRes.builder()
                                .token(createdConfirmationToken.getToken())
                                .expiresAt(createdConfirmationToken.getExpiresAt())
                                .build()
                ).build();
    }

    private void validateRequest(final RegistrationPayloadRequest accountPayloadReq) {
        final Predicate<String> emailValidator = new EmailValidator();
        final Predicate<String> passwordValidator = new PasswordValidator();
        final Predicate<String> usernameValidator = new UsernameValidator();

        if (!emailValidator.test(accountPayloadReq.getEmail())) throw new InvalidEmailException();
        if (!passwordValidator.test(accountPayloadReq.getEmail())) throw new InvalidPasswordException();
        if (!usernameValidator.test(accountPayloadReq.getEmail())) throw new InvalidUsernameException();
    }

    private UserAccountEntity buildEntityFromRequest(final RegistrationPayloadRequest req) {
        return UserAccountEntity.builder()
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .phoneNumber(req.getPhoneNumber())
                .username(req.getUsername())
                .password(new BCryptPasswordEncoder().encode(req.getPassword()))
                .email(req.getEmail())
                .createdAt(LocalDateTime.now())
                .removed(false)
                .enabled(false)
                .build();
    }

    private RegistrationEventPayload buildRegistrationEvent(UserAccountEntity userAccount, ConfirmationTokenEntity confirmationToken) {
        return RegistrationEventPayload.builder()
                .firstname(userAccount.getFirstname())
                .lastname(userAccount.getLastname())
                .email(userAccount.getEmail())
                .username(userAccount.getUsername())
                .confirmationToken(confirmationToken.getToken())
                .expirationTime(confirmationToken.getExpiresAt())
                .build();
    }
}
