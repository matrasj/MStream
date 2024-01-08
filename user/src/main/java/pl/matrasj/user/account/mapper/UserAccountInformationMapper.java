package pl.matrasj.user.account.mapper;

import pl.matrasj.user.account.UserAccountEntity;
import pl.matrasj.user.account.payload.UserAccountInformationPayload;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class UserAccountInformationMapper {
    public static UserAccountInformationPayload toUserAccountInformationPayload(UserAccountEntity userAccount) {
        String avatarImg = null;
        if (userAccount.getAvatarPathImg() != null) {
            try {
                byte[] avatarImgAsByteArray = Files.readAllBytes(Paths.get(userAccount.getAvatarPathImg()));
                avatarImg = Base64.getEncoder().encodeToString(avatarImgAsByteArray);
            } catch (IOException e) {
                throw new IllegalStateException("File doest not exists");
            }
        }
        return UserAccountInformationPayload.builder()
                .firstname(userAccount.getFirstname())
                .lastname(userAccount.getLastname())
                .email(userAccount.getEmail())
                .phoneNumber(userAccount.getPhoneNumber())
                .avatarImg(avatarImg)
                .isAssignedForNewsletter(userAccount.getIsAssignedForNewsletter())
                .build();
    }

    public static List<UserAccountInformationPayload> toUserAccountInformationPayload(List<UserAccountEntity> userAccounts) {
        return userAccounts
                .stream()
                .map(UserAccountInformationMapper::toUserAccountInformationPayload)
                .collect(Collectors.toList());
    }
}
