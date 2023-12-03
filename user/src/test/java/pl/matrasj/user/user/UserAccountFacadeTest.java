package pl.matrasj.user.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matrasj.user.user.account.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class UserAccountFacadeTest {
    @InjectMocks
    UserAccountFacade userAccountFacade;
    @Mock
    UserAccountRepository userAccountRepository;

    public void givenUserAccountPayloadRequest_whenRegisteringNewUserAccount_shouldRegisterAccountAndReturnConfirmationToken() {
        // Given
        UserAccountPayloadReq userAccountRequest = UserAccountPayloadReq.builder()
                .username("matrasj")
                .email("test.test@gmail.com")
                .password("Password123!")
                .firstname("John")
                .lastname("Doe")
                .phoneNumber("111222333")
                .build();
        when(userAccountRepository.save(any())).thenReturn(UserAccountEntity.builder()
                .username("matrasj")
                .email("test.test@gmail.com")
                .password("Password123!")
                .firstname("John")
                .lastname("Doe")
                .phoneNumber("111222333")
                .build());
        // When
        UserAccountPayloadRes requestResponse = userAccountFacade.registerAccount(userAccountRequest);
        // Then
        assertEquals("matrasj", requestResponse.getUsername(), "Invalid username");
        assertEquals("test.test@gmail.com", requestResponse.getEmail(), "Invalid email");
        assertNotNull(requestResponse.getConfirmationToken().getToken(), "Token is null");

    }
}