package com.example.BoardService.question;

import com.example.BoardService.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // 질문리스트 조회
    public List<Question> getList(){
        return questionRepository.findAll();
    }

    // id 값으로 질문 조회
    public Optional<Question> getQuestion(Integer id){
        return questionRepository.findById(id);
    }

    // 질문 생성
    public Question create(String subject, String content, SiteUser user){

        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(user);
        return questionRepository.save(question);
    }

    // 페이징 처리
    public Page<Question> getList(int page) {

        List<Sort.Order> sorts= new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        // 한 페이지에 10개씩 보여주고, 최신순으로 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> questionList = this.questionRepository.findAll(pageable);
        return questionList;
    }


    // 질문 수정
    public Question modify(Question question, String subject, String content){

        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());

        return questionRepository.save(question);
    }
}
