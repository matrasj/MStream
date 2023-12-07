package pl.matrasj.user.confirmationtoken;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import pl.matrasj.user.account.UserAccountEntity;
import pl.matrasj.user.account.UserAccountRepository;
import pl.matrasj.user.confirmationtoken.exception.ConfirmationTokenAlreadyConfirmedException;
import pl.matrasj.user.confirmationtoken.exception.ConfirmationTokenAlreadyExpiredException;
import pl.matrasj.user.infrastructure.EntityNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConfirmationTokenFacade {
    public static final String SUCCESSFULLY_CONFIRMATION_MESSAGE = "Successfully enabled account";
    ConfirmationTokenRepository confirmationTokenRepository;
    UserAccountRepository userAccountRepository;

    @Transactional
    public String confirmAccount(String token) {
        ConfirmationTokenEntity confirmationToken
                = confirmationTokenRepository.findByToken(token).orElseThrow(EntityNotFoundException::new);

        if (confirmationToken.getConfirmedAt() != null) throw new ConfirmationTokenAlreadyConfirmedException();
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) throw new ConfirmationTokenAlreadyExpiredException();

        UserAccountEntity userAccount
                = userAccountRepository.findById(confirmationToken.getUserAccountId()).orElseThrow(EntityNotFoundException::new);
        confirmationToken.confirm();
        userAccount.enable();
        return SUCCESSFULLY_CONFIRMATION_MESSAGE;
    }
}
