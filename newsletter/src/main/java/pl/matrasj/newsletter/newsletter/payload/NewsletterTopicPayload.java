package pl.matrasj.newsletter.newsletter.payload;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NewsletterTopicPayload {
    private List<UserAccountInformationPayload> users;
    private NewsletterSendPayloadRequest request;
}
