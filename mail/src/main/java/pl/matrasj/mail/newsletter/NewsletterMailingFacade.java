package pl.matrasj.mail.newsletter;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsletterMailingFacade {
    private final JavaMailSender javaMailSender;
    @Value("${email.properties.our-email}")
    private String ourEmail;
    public void sendMailAboutNewsletter(NewsletterTopicPayload newsletterTopicPayload) {
        List<UserAccountInformationPayload> users = newsletterTopicPayload.getUsers();
        NewsletterSendPayloadRequest newsletterRequest = newsletterTopicPayload.getRequest();
        for (UserAccountInformationPayload user: users) {
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                    helper.setText(newsletterRequest.getEmailContent()
                            .replace("{{userFirstnameWithLastname}}", String.format("%s %s", user.getFirstname(), user.getLastname())), true);
                    helper.setTo(user.getEmail());
                    helper.setSubject(newsletterRequest.getMailSubject());
                    helper.setFrom(ourEmail);
                    javaMailSender.send(mimeMessage);
                } catch (MessagingException messagingException) {
                    throw new IllegalStateException("Failed to send email to: " + user.getEmail());
                }
        }
    }
}
