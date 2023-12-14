package pl.matrasj.newsletter.newsletter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matrasj.newsletter.feign.UserAccountFeign;
import pl.matrasj.newsletter.infrastructure.NoPermissionToResourceException;
import pl.matrasj.newsletter.newsletter.payload.NewsletterResultPayloadResponse;
import pl.matrasj.newsletter.newsletter.payload.NewsletterSendPayloadRequest;
import pl.matrasj.newsletter.newsletter.payload.UserAccountInformationPayload;

import java.util.List;

@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NewsletterController {
    UserAccountFeign userAccountFeign;
    NewsletterFacade newsletterFacade;
    @PostMapping
    public ResponseEntity<NewsletterResultPayloadResponse> sendNewsletter(@RequestBody NewsletterSendPayloadRequest request,
                                                                          @RequestHeader("Authorization") String bearerToken) {
        authenticate(userAccountFeign.isAdmin(bearerToken));
        List<UserAccountInformationPayload> usersAssignedForNewsLetter = userAccountFeign.getUsersAssignedForNewsletter(bearerToken).getBody();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(newsletterFacade.sendNewsletter(request, usersAssignedForNewsLetter));
    }

    private void authenticate(ResponseEntity<Boolean> isAdminResponseEntity) {
        if (HttpStatus.FORBIDDEN.equals(isAdminResponseEntity.getStatusCode())) {
            throw new NoPermissionToResourceException();
        } else if (!Boolean.TRUE.equals(isAdminResponseEntity.getBody())){
            throw new ErrorOccurredDuringCommunication();
        }
    }
}
