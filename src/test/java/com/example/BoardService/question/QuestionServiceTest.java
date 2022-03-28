package com.example.BoardService.question;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void testCreateData(){

        for (int i = 0; i <= 300; i++) {
            String subject = String.format("testSubjectData [%03d]", i);
            String content = "testContentData";
            questionService.create(subject, content,null);

        }
    }

}