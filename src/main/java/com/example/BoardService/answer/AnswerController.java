package com.example.BoardService.answer;

import com.example.BoardService.question.Question;
import com.example.BoardService.question.QuestionService;
import com.example.BoardService.user.SiteUser;
import com.example.BoardService.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    // post_create
    @PreAuthorize("isAuthenticated()") // 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Integer id, @RequestParam String content,
                                @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
                // Principal: 현재 로그인한 사용자에 대한 정보를 알기 위해 사용 (스프링 시큐리티가 제공, SecurityConfig 에서 설정)
                // principal 객체는 로그인을 해야만 생성되는 객체
                // principal.getName(): 현재 로그인한 사용자의 이름

        // 질문생성
        Optional<Question> question = questionService.getQuestion(id);

        // principal 객체를 통해 사용자명을 얻음
        Optional<SiteUser> user = userService.getUser(principal.getName());

        if(question.isPresent()  && user.isPresent() ) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("question", question.get());
                return "question_detail";
            }
            // 답변, 유저 저장
            answerService.create(question.get(), answerForm.getContent(), user.get());
            return String.format("redirect:/question/detail/%s", id);
        } else{
            return new ResponseStatusException(HttpStatus.NOT_FOUND).getReason();
        }

    }



}
