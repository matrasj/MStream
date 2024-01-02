package pl.matrasj.quiz.quizquestion;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matrasj.quiz.quizquestion.dto.QuizQuestionDto;
import pl.matrasj.quiz.quizquestion.dto.QuizQuestionWithInfoAboutCorrectAnswersDto;
import pl.matrasj.quiz.quizquestion.payload.QuizGenerationPayloadRequest;
import pl.matrasj.quiz.quizquestion.payload.QuizSolvedPayloadRequest;
import pl.matrasj.quiz.quizquestion.payload.QuizSolvedPayloadResponse;
import pl.matrasj.quiz.shared.ListDto;
import pl.matrasj.quiz.shared.PaginationRequestPayload;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-question")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizQuestionController {
    QuizQuestionRepository quizQuestionRepository;
    QuizSolverFacade quizSolverFacade;

    @PostMapping
    public ResponseEntity<List<QuizQuestionDto>> getQuiz(@RequestBody @Valid QuizGenerationPayloadRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(QuizQuestionMapper.toDto(
                        quizQuestionRepository.getRandomActiveQuestionsByCategories(
                                request.getQuizCategoryIds(),
                                request.getCount())
                ));
    }

    @PostMapping("/solution")
    public ResponseEntity<QuizSolvedPayloadResponse> solveQuiz(@RequestBody @Valid QuizSolvedPayloadRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(quizSolverFacade.solveQuiz(request));
    }

    @PostMapping("/quiz-category/{quizCategoryId}")
    public ResponseEntity<ListDto<QuizQuestionWithInfoAboutCorrectAnswersDto>> getQuestionsByQuizCategory(@PathVariable Long quizCategoryId,
                                                                                                          @RequestBody PaginationRequestPayload paginationRequest) {
        Page<QuizQuestionEntity> page = quizQuestionRepository.findAllActiveByQuizCategoryId(
                quizCategoryId,
                PageRequest.of(paginationRequest.getPage(), paginationRequest.getItemsPerPage()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ListDto.<QuizQuestionWithInfoAboutCorrectAnswersDto>builder()
                        .items(QuizQuestionMapper.toDtoWithCorrectInfo(page.getContent()))
                        .totalCount(page.getTotalElements())
                        .build());
    }
}
