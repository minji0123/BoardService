package com.example.BoardService.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository: 데이터 처리를 위해서는 실제 데이터베이스와 연동
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
}
