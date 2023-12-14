package pl.matrasj.mail.newsletter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsletterSendPayloadRequest {
    private String mailSubject;
    private String emailContent;
}