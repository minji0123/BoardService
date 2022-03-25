package com.example.BoardService.answer;

import com.example.BoardService.question.Question;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
    // 답변 모델에서 질문 모델을 참조하기 위해 추가 (부모는 Question, 자식은 Answer)
    // ex) answer.getQuestion().getSubject()

}
