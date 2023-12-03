package pl.matrasj.user.user.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAccountFacade {
    private final UserAccountRepository userAccountRepository;
    public UserAccountPayloadRes registerAccount(UserAccountPayloadReq accountPayloadReq) {
        
    }
}
