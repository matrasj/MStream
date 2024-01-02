package pl.matrasj.quiz.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationRequestPayload {
    private int itemsPerPage;
    private int page;
}
