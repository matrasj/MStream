package pl.matrasj.user.account.kafka.registration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pl.matrasj.user.account.kafka.config.KafkaPropertiesConfig;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaRegistrationEventProducerImpl implements KafkaRegistrationEventProducer{
    KafkaTemplate<String, RegistrationEventPayload> kafkaTemplate;
    KafkaPropertiesConfig kafkaPropertiesConfig;
    @Override
    public void pushRegistrationEvent(RegistrationEventPayload registrationEventPayload) {
        Message<RegistrationEventPayload> message = MessageBuilder
                .withPayload(registrationEventPayload)
                .setHeader(KafkaHeaders.TOPIC, this.kafkaPropertiesConfig.getRegistrationsTopicName())
                .build();
        this.kafkaTemplate.send(message);
    }
}
