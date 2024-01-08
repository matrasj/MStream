package pl.matrasj.user.account;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.matrasj.user.account.kafka.KafkaRegistrationEventProducer;
import pl.matrasj.user.account.kafka.RegistrationEventPayload;
import pl.matrasj.user.account.mapper.UserAccountInformationMapper;
import pl.matrasj.user.account.payload.RegistrationPayloadRequest;
import pl.matrasj.user.account.payload.RegistrationPayloadResponse;
import pl.matrasj.user.account.payload.UserAccountInformationPayload;
import pl.matrasj.user.account.validators.PasswordValidator;
import pl.matrasj.user.account.exception.InvalidEmailException;
import pl.matrasj.user.account.exception.InvalidPasswordException;
import pl.matrasj.user.account.exception.UserAccountAlreadyExistsException;
import pl.matrasj.user.account.validators.EmailValidator;
import pl.matrasj.user.authentication.Role;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenEntity;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenFactory;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenPayloadResponse;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenRepository;
import pl.matrasj.user.infrastructure.FileSaver;
import pl.matrasj.user.shared.EntityNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;


@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountFacade {
    private static final String AVATAR_UPLOAD_DIR = "C://avatars";
    UserAccountRepository userAccountRepository;
    ConfirmationTokenFactory confirmationTokenFactory;
    ConfirmationTokenRepository confirmationTokenRepository;
    KafkaRegistrationEventProducer kafkaRegistrationEventProducer;
    FileSaver fileSaver;

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
                .confirmationToken(
                        ConfirmationTokenPayloadResponse.builder()
                                .token(createdConfirmationToken.getToken())
                                .expiresAt(createdConfirmationToken.getExpiresAt())
                                .build()
                ).build();
    }

    public List<UserAccountEntity> getAllUserAccountsAssignedForNewsletter() {
        return userAccountRepository.findAllUsersAssignedForNewsletter();
    }

    public UserAccountEntity findUserAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public UserAccountInformationPayload changeAvatar(String email, MultipartFile file) throws IOException {
        UserAccountEntity userAccount = findUserAccountByEmail(email);
        String fileName = "avatar_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        String pathToAvatarImg = fileSaver.saveFile(file.getBytes(), AVATAR_UPLOAD_DIR, fileName);
        userAccount.setAvatarPathImg(pathToAvatarImg);
        return UserAccountInformationMapper.toUserAccountInformationPayload(userAccount);
    }

    @Transactional
    public UserAccountInformationPayload updatePersonalData(String email, UserAccountInformationPayload updatedData) {
        UserAccountEntity userAccount = findUserAccountByEmail(email);

        userAccount.setFirstname(updatedData.getFirstname());
        userAccount.setLastname(updatedData.getLastname());
        userAccount.setPhoneNumber(updatedData.getPhoneNumber());
        return UserAccountInformationMapper.toUserAccountInformationPayload(userAccount);
    }

    @Transactional
    public UserAccountInformationPayload toggleNewsletterActivity(String email) {
        UserAccountEntity userAccount = findUserAccountByEmail(email);

        userAccount.setIsAssignedForNewsletter(!userAccount.getIsAssignedForNewsletter());
        return UserAccountInformationMapper.toUserAccountInformationPayload(userAccount);
    }

    private void validateRequest(final RegistrationPayloadRequest accountPayloadReq) {
        final Predicate<String> emailValidator = new EmailValidator();
        final Predicate<String> passwordValidator = new PasswordValidator();

        if (!emailValidator.test(accountPayloadReq.getEmail())) throw new InvalidEmailException();
        if (!passwordValidator.test(accountPayloadReq.getEmail())) throw new InvalidPasswordException();
    }

    private UserAccountEntity buildEntityFromRequest(final RegistrationPayloadRequest req) {
        return UserAccountEntity.builder()
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .phoneNumber(req.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(req.getPassword()))
                .email(req.getEmail())
                .createdAt(LocalDateTime.now())
                .removed(false)
                .enabled(false)
                .hasAccessToCourse(false)
                .role(Role.CUSTOMER)
                .isAssignedForNewsletter(false)
                .build();
    }

    private RegistrationEventPayload buildRegistrationEvent(UserAccountEntity userAccount, ConfirmationTokenEntity confirmationToken) {
        return RegistrationEventPayload.builder()
                .firstname(userAccount.getFirstname())
                .lastname(userAccount.getLastname())
                .email(userAccount.getEmail())
                .confirmationToken(confirmationToken.getToken())
                .expirationTime(confirmationToken.getExpiresAt())
                .build();
    }
}
