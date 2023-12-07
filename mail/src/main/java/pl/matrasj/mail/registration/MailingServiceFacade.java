package pl.matrasj.mail.registration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailingServiceFacade {
    public static final String SUBJECT = "Account confirmation";
    private final JavaMailSender javaMailSender;
    @Value("${email.properties.our-email}")
    private String ourEmail;
    @Value("${email.properties.confirmation-token-url}")
    private String confirmationTokenUrl;
    void sendMailAboutAccountConfirmation(RegistrationEventPayload registrationEventPayload) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(
                    MailBodyTemplateFactory.buildTemplateForEmailConfirmation(
                            String.format("%s %s", registrationEventPayload.getFirstname(), registrationEventPayload.getLastname()),
                            confirmationTokenUrl
                    ), true);
            helper.setTo(registrationEventPayload.getEmail());
            helper.setSubject(SUBJECT);
            helper.setFrom(ourEmail);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            throw new IllegalStateException("Failed to send email to: " + registrationEventPayload.getEmail());
        }
    }
}
