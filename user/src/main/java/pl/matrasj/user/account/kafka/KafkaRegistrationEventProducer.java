package pl.matrasj.user.account.kafka;

public interface KafkaRegistrationEventProducer {
    void pushRegistrationEvent(RegistrationEventPayload registrationEventPayload);
}
