package pl.matrasj.user.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matrasj.user.account.*;
import pl.matrasj.user.account.UserAccountRepository;
import pl.matrasj.user.account.kafka.KafkaRegistrationEventProducer;
import pl.matrasj.user.account.payload.RegistrationPayloadRequest;
import pl.matrasj.user.account.payload.RegistrationPayloadResponse;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenEntity;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenFactory;
import pl.matrasj.user.confirmationtoken.ConfirmationTokenRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAccountFacadeTest {
    @InjectMocks
    UserAccountFacade userAccountFacade;
    @Mock
    UserAccountRepository userAccountRepository;
    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    ConfirmationTokenFactory confirmationTokenFactory;
    @Mock
    KafkaRegistrationEventProducer kafkaRegistrationEventProducer;

    @Test
    public void givenUserAccountPayloadRequest_whenRegisteringNewUserAccount_shouldRegisterAccountAndReturnConfirmationToken() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        RegistrationPayloadRequest userAccountRequest = RegistrationPayloadRequest.builder()
                .email("test.test@gmail.com")
                .password("Password123!")
                .firstname("John")
                .lastname("Doe")
                .phoneNumber("111222333")
                .build();
        when(userAccountRepository.save(any()))
                .thenReturn(UserAccountEntity.builder()
                .email("test.test@gmail.com")
                .password("Password123!")
                .firstname("John")
                .lastname("Doe")
                .phoneNumber("111222333")
                .build());
        when(confirmationTokenRepository.save(any()))
                .thenReturn(ConfirmationTokenEntity.builder()
                        .token("1234-1234")
                        .expiresAt(now)
                        .build());

        // When
        RegistrationPayloadResponse requestResponse = userAccountFacade.registerAccount(userAccountRequest);
        // Then
        assertEquals("test.test@gmail.com", requestResponse.getEmail(), "Invalid email");
        assertEquals(requestResponse.getConfirmationToken().getToken(), "1234-1234", "Invalid token");
        assertEquals(requestResponse.getConfirmationToken().getExpiresAt(), now, "Invalid expiration date");
    }
}