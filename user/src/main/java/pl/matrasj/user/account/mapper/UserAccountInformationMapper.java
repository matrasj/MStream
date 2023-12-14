package pl.matrasj.user.account.mapper;

import pl.matrasj.user.account.UserAccountEntity;
import pl.matrasj.user.account.payload.UserAccountInformationPayload;

import java.util.List;
import java.util.stream.Collectors;

public class UserAccountInformationMapper {
    public static UserAccountInformationPayload toUserAccountInformationPayload(UserAccountEntity userAccount) {
        return UserAccountInformationPayload.builder()
                .firstname(userAccount.getFirstname())
                .lastname(userAccount.getLastname())
                .email(userAccount.getEmail())
                .build();
    }

    public static List<UserAccountInformationPayload> toUserAccountInformationPayload(List<UserAccountEntity> userAccounts) {
        return userAccounts
                .stream()
                .map(UserAccountInformationMapper::toUserAccountInformationPayload)
                .collect(Collectors.toList());
    }
}
