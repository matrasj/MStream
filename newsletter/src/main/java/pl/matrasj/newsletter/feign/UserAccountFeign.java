package pl.matrasj.newsletter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import pl.matrasj.newsletter.newsletter.payload.UserAccountInformationPayload;

import java.util.List;

@FeignClient(value = "user", path = "/api")
public interface UserAccountFeign {
    @GetMapping("/user-account/assigned-for-newsletter")
    ResponseEntity<List<UserAccountInformationPayload>> getUsersAssignedForNewsletter(@RequestHeader(name = "Authorization") String bearerToken);

    @GetMapping("/authentication/is-admin")
    ResponseEntity<Boolean> isAdmin(@RequestHeader(name = "Authorization") String bearerToken);
}
