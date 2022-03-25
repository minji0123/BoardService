package com.example.BoardService.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
