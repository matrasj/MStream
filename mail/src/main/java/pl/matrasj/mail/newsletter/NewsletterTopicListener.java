package pl.matrasj.mail.newsletter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.matrasj.mail.useraccount.RegistrationEventPayloadSerializationException;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsletterTopicListener {
    NewsletterMailingFacade newsletterMailingFacade;
    ObjectMapper objectMapper;
    @KafkaListener(topics = "${kafka.topics.newsletter-topic-name}", groupId = "${kafka.groups.group-id}")
    public void listenForNewsletter(String newsletterTopicPayloadAsJson) {
        try {
            NewsletterTopicPayload newsletterTopicPayload = objectMapper.readValue(newsletterTopicPayloadAsJson, NewsletterTopicPayload.class);
            newsletterMailingFacade.sendMailAboutNewsletter(newsletterTopicPayload);
        } catch (JsonProcessingException e) {
            log.error(String.format("JSON: %s can not be serialized into RegistrationEventPayload object instance.", newsletterTopicPayloadAsJson));
            throw new RegistrationEventPayloadSerializationException();
        }
    }
}
