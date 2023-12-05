package pl.matrasj.user.account.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class KafkaUserAccountRegistrationTopicConfig {
    KafkaPropertiesConfig kafkaPropertiesConfig;

    @Bean
    public NewTopic createKafkaRegistrationTopic() {
        return TopicBuilder
                .name(this.kafkaPropertiesConfig.getRegistrationsTopicName())
                .partitions(2)
                .build();
    }
}
