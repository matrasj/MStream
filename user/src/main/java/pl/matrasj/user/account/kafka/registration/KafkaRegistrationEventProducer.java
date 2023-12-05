package pl.matrasj.user.account.kafka.registration;

public interface KafkaRegistrationEventProducer {
    void pushRegistrationEvent(RegistrationEventPayload registrationEventPayload);
}
