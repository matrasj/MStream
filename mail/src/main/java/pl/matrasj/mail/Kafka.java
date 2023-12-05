package pl.matrasj.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class Kafka {
    @KafkaListener(topics = "register_events", groupId = "my-group")
    public void x(String d) {
        log.info(d);
    }
}
