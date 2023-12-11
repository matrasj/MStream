package pl.matrasj.course;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Test {
    AuthenticationServiceFeign authenticationServiceFeign;
    @GetMapping
    public String invokeTest(final HttpServletRequest request) {
        return String.format("Has permission: %s", authenticationServiceFeign.hasPermissionForCourse(request.getHeader(AUTHORIZATION)).getBody());
    }

}
