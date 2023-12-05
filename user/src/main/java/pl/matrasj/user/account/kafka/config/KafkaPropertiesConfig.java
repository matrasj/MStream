package pl.matrasj.user.account.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KafkaPropertiesConfig {
    @Value("${kafka.topic.registrations.name}")
    private String registrationsTopicName;
    @Value("${kafka.topic.registrations.partitions}")
    private int partitions;
}
