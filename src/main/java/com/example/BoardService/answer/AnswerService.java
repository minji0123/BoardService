package com.example.BoardService.answer;

import com.example.BoardService.question.Question;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    // 질문에 대한 답변을 저장한다.
    public Answer create(Question question, String content) {
        // 파라미터_content: 템플릿에서 답변으로 입력한 내용(content)을 얻기 위해 추가한 변수

        // 답변 객체를 생성하고
        // setter 를 해주고
        // repository 를 사용해서 저장한다.

        Answer answer = new Answer();
//        answer.setContent(answer.getContent()); 파라미터로 넘어온 content 를 set 해야 하기 때문에 getter 를 사용하면 안됨
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);

        return answerRepository.save(answer);
    }
}
