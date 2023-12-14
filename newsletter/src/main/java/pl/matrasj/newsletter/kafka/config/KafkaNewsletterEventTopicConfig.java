package pl.matrasj.newsletter.kafka.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaNewsletterEventTopicConfig {
    KafkaPropertiesConfig kafkaPropertiesConfig;
    @Bean
    public NewTopic createKafkaRegistrationTopic() {
        return TopicBuilder
                .name(kafkaPropertiesConfig.getNewsletterTopicName())
                .partitions(kafkaPropertiesConfig.getPartitions())
                .build();
    }
}
