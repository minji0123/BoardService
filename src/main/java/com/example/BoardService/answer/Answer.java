package com.example.BoardService.answer;

import com.example.BoardService.question.Question;
import com.example.BoardService.user.SiteUser;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
// Entity for Answer
@Entity
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne
    private Question question;
    // 답변 모델에서 질문 모델을 참조하기 위해 추가 (부모는 Question, 자식은 Answer)
    // ex) answer.getQuestion().getSubject()

    // 한명이 여러 질문을 작성할 수 있다.
    @ManyToOne
    private SiteUser author;
}
