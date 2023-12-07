package pl.matrasj.user.account.kafka.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pl.matrasj.user.account.kafka.config.KafkaPropertiesConfig;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaRegistrationEventProducerImpl implements KafkaRegistrationEventProducer{
    KafkaTemplate<String, String> kafkaTemplate;
    KafkaPropertiesConfig kafkaPropertiesConfig;
    ObjectMapper objectMapper;

    @Override
    public void pushRegistrationEvent(RegistrationEventPayload registrationEventPayload) {
        try {
            kafkaTemplate.send(
                    MessageBuilder
                            .withPayload(objectMapper.writeValueAsString(registrationEventPayload))
                            .setHeader(KafkaHeaders.TOPIC, kafkaPropertiesConfig.getRegistrationsTopicName())
                            .build()
            );
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RegistrationEventPayloadSerializationException();
        }
    }

}
