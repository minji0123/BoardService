package com.example.BoardService.Repository;

import com.example.BoardService.question.Question;
import com.example.BoardService.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionCRTest {

    @Autowired
    // @Autowired 애너테이션은 스프링의 DI 기능
    // DI(Dependency Injection) - 스프링이 객체를 대신 생성하여 주입한다.
    private QuestionRepository questionRepository;

    @Test
    void testJpaCreateQuestion(){
        Question q1 = new Question();
        q1.setSubject("제목1");
        q1.setContent("내용1");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("제목2");
        q2.setContent("내용2");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);
    }

    @Test
    void testJpaFindQuestion(){
        List<Question> questions = questionRepository.findAll();
        assertEquals(2, questions.size());

        Question question = questions.get(0);
        assertEquals("제목1", question.getSubject());
    }

    @Test
    void testJpaFindId(){
        //  findById의 리턴 타입은 Question이 아닌 Optional
        // Optional 로 받지 않을 경우, orElse(null) 처리를 하면 됨
        Optional<Question> oQuestion = questionRepository.findById(1);

        // Optional 을 사용하면 아래와 같이 처리 가능
        // isPresent ==  (!= null)
        if(oQuestion.isPresent()){
            Question question = oQuestion.get();
            System.out.println(question);
            assertEquals("제목1", question.getSubject());

        }
    }

    @Test
    void testJpaFindSubject(){
        Question question = questionRepository.findBySubject("제목1");
        assertEquals(1, question.getId());
    }

    @Test
    void testJpaFindSubjectAndContent(){
        Question question = questionRepository.findBySubjectAndContent("제목1", "내용1");
        assertEquals(1, question.getId());
    }

    @Test
    void testJpaFindSubjectLike(){
        List<Question> questions = questionRepository.findBySubjectLike("제목%");
        Question question = questions.get(0);
        assertEquals("제목1", question.getSubject());
    }

}
