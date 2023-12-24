package pl.matrasj.quiz.quizcategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "quiz_category")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizCategoryEntity {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;
}
