package pl.matrasj.mail.useraccount;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountMailingFacade {
    private static final String SUBJECT = "Account confirmation";
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
                    UserAccountMailBodyTemplateFactory.buildTemplateForEmailConfirmation(
                            String.format("%s %s", registrationEventPayload.getFirstname(), registrationEventPayload.getLastname()),
                            String.format("%s?token=%s", confirmationTokenUrl, registrationEventPayload.getConfirmationToken())
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
