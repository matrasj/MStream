package pl.matrasj.newsletter.newsletter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.matrasj.newsletter.kafka.KafkaNewsletterEventProducer;
import pl.matrasj.newsletter.newsletter.payload.NewsletterResultPayloadResponse;
import pl.matrasj.newsletter.newsletter.payload.NewsletterSendPayloadRequest;
import pl.matrasj.newsletter.newsletter.payload.NewsletterTopicPayload;
import pl.matrasj.newsletter.newsletter.payload.UserAccountInformationPayload;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsletterFacade {
    KafkaNewsletterEventProducer kafkaNewsletterEventProducer;
    public NewsletterResultPayloadResponse sendNewsletter(NewsletterSendPayloadRequest request, List<UserAccountInformationPayload> usersAssignedForNewsLetter) {
        int usersAssignedForNewsLetterSize = usersAssignedForNewsLetter != null ? usersAssignedForNewsLetter.size() : 0;
        NewsletterResultPayloadResponse response = NewsletterResultPayloadResponse.builder()
                .message(String.format("Push info to topic about sending newsletter to %s users.", usersAssignedForNewsLetterSize))
                .build();
        if (usersAssignedForNewsLetter != null && !usersAssignedForNewsLetter.isEmpty()) {
            kafkaNewsletterEventProducer.pushNewsletterEvent(NewsletterTopicPayload.builder()
                    .users(usersAssignedForNewsLetter)
                    .request(request)
                    .build());
            return response;
        }
        return response;
    }
}
