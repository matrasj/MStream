package pl.matrasj.mail.registration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaTopicListener {
    MailingServiceFacade mailingServiceFacade;
    ObjectMapper objectMapper;
    @KafkaListener(topics = "${kafka.topics.registration-topic-name}", groupId = "${kafka.groups.group-id}")
    public void listenForRegistrationEvents(String registrationPayloadAsJson) {
        try {
            RegistrationEventPayload registrationEventPayload = objectMapper.readValue(registrationPayloadAsJson, RegistrationEventPayload.class);
            mailingServiceFacade.sendMailAboutAccountConfirmation(registrationEventPayload);
        } catch (JsonProcessingException e) {
            log.error(String.format("JSON: %s can not be serialized into RegistrationEventPayload object instance.", registrationPayloadAsJson));
            throw new RegistrationEventPayloadSerializationException ();
        }
    }
}
