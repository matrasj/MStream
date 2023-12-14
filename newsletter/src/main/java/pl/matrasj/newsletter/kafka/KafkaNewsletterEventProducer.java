package pl.matrasj.newsletter.kafka;

import pl.matrasj.newsletter.newsletter.payload.NewsletterTopicPayload;

public interface KafkaNewsletterEventProducer {
    void pushNewsletterEvent(NewsletterTopicPayload newsletterTopicPayload);
}
