package com.example.BoardService.Repository;

import com.example.BoardService.answer.Answer;
import com.example.BoardService.question.Question;
import com.example.BoardService.answer.AnswerRepository;
import com.example.BoardService.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testJpaCreateAnswer() {
        // id 2 인 질문을 찾아서 답변을 추가한다.
        Optional<Question> oQuestion = this.questionRepository.findById(2);
        assertTrue(oQuestion.isPresent());
        Question question = oQuestion.get();

        Answer answer = new Answer();
        answer.setContent("답변1");
        answer.setQuestion(question);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    @Test
    void testJpaFindAnswer(){
        Optional<Answer> oAnswer = this.answerRepository.findById(1);
        assertTrue(oAnswer.isPresent());
        Answer answer = oAnswer.get();
        assertEquals(2, answer.getQuestion().getId());
    }


    // Transactional
    // Question 리포지터리가 findById를 호출하여 Question 객체를 조회하고 나면 DB세션이 끊어져서 오류 발생. 그 이후에 실행되는 question.getAnswerList(); 메서드는 세션이 종료되어 오류가 발생한다.
    // 이 문제는 테스트 코드에서만 발생한다. 실제 서버에서 JPA 프로그램들을 실행할 때는 세션이 종료되지 않기 때문에 위와 같은 오류가 발생하지 않는다.
    @Test
    @Transactional
    void testJpa() {
        Optional<Question> oQuestion = this.questionRepository.findById(2);
        assertTrue(oQuestion.isPresent());
        Question question = oQuestion.get();

        List<Answer> answerList = question.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

}