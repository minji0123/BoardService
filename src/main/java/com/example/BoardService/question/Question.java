package com.example.BoardService.question;

import com.example.BoardService.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // GeneratedValue: 데이터를 저장할 때 해당 속성에 값을 따로 세팅하지 않아도 1씩 자동으로 증가
    // strategy는 고유번호를 생성하는 옵션, GenerationType.IDENTITY는 해당 컬럼만의 독립적인 시퀀스를 생성
    private Integer id;

    @Column(length = 200)
    //  테이블 컬럼으로 인식하고 싶지 않은 경우에만 @Transient 애너테이션을 사용
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "question")
    // mappedBy는 참조 모델의 속성명을 의미
    // Answer 모델에서 Questoin 모델을 참조한 속성명 question을 mappedBy에 전달해야 한다.
    private List<Answer> answerList;
    // 답변 모델에서 질문 모델을 참조하기 위해 추가 (부모는 Question, 자식은 Answer)
    // question.getAnswerList()

}
