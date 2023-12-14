package pl.matrasj.course;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user", path = "/api/authentication")
public interface AuthenticationServiceFeign {
    @GetMapping("/permission-for-course")
    ResponseEntity<Boolean> hasPermissionForCourse(@RequestHeader(name = "Authorization") String token);
}
