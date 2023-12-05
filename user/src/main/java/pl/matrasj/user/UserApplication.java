package pl.matrasj.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RequestMapping("")
@RestController
public class UserApplication {

    @GetMapping("/test")
    public String x() {
        return "TEST";
    }
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
