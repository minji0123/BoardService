package com.example.BoardService.Repository;

import com.example.BoardService.question.Question;
import com.example.BoardService.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuestionUDTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testJpaUpdateQuestion(){
        // id 1 값을 가진 Question 객체를 조회
        Optional<Question> oQuestion = questionRepository.findById(1);
        // question 이 null 이 아닐 때, 값이 true 인지 확인하고, 값이 없으면 테스트 오류를 발생시킨다.
        // 오류가 발생하지 않는다면 다음 단계 실행
        assertTrue(oQuestion.isPresent());

        Question question = oQuestion.get();
        question.setSubject("수정된 제목");
        questionRepository.save(question);
    }

    @Test
    void testJpaDeleteQuestion(){
        // 일단 데이터가 2개 존재하는지 확인하고, 아니면 테스트 오류를 발생시킨다.
        assertEquals(2,questionRepository.count());
        Optional<Question> oQuestion = questionRepository.findById(1);
        assertTrue(oQuestion.isPresent());

        Question question = oQuestion.get();
        questionRepository.delete(question);
        assertEquals(1,questionRepository.count());
    }
}