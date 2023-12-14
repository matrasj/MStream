package pl.matrasj.newsletter.newsletter.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsletterSendPayloadRequest {
    private String mailSubject;
    private String emailContent;
}
