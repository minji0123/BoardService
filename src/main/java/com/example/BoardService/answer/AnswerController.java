package com.example.BoardService.answer;

import com.example.BoardService.question.Question;
import com.example.BoardService.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    // post_create
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Integer id, @RequestParam String content,
                                @Valid AnswerForm answerForm, BindingResult bindingResult) {

        Optional<Question> question = questionService.getQuestion(id);

        if(question.isPresent()) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("question", question.get());
                return "question_detail";
            }
            answerService.create(question.get(),  answerForm.getContent()); // 답변 저장
            return String.format("redirect:/question/detail/%s", id);
        } else{
            return new ResponseStatusException(HttpStatus.NOT_FOUND).getReason();
        }

    }



}
