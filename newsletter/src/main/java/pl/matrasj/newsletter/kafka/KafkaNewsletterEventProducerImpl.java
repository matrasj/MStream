package pl.matrasj.newsletter.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pl.matrasj.newsletter.kafka.config.KafkaPropertiesConfig;
import pl.matrasj.newsletter.newsletter.payload.NewsletterSendPayloadRequest;
import pl.matrasj.newsletter.newsletter.payload.NewsletterTopicPayload;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaNewsletterEventProducerImpl implements KafkaNewsletterEventProducer {
    KafkaTemplate<String, String> kafkaTemplate;
    KafkaPropertiesConfig kafkaPropertiesConfig;
    ObjectMapper objectMapper;

    @Override
    public void pushNewsletterEvent(NewsletterTopicPayload newsletterTopicPayload) {
        try {
            kafkaTemplate.send(
                    MessageBuilder
                            .withPayload(objectMapper.writeValueAsString(newsletterTopicPayload))
                            .setHeader(KafkaHeaders.TOPIC, kafkaPropertiesConfig.getNewsletterTopicName())
                            .build()
            );
        } catch (JsonProcessingException jsonProcessingException) {
            throw new NewsletterEventPayloadSerializationException();
        }
    }
}
