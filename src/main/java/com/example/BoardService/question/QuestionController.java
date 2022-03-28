package com.example.BoardService.question;

import com.example.BoardService.answer.AnswerForm;
import com.example.BoardService.user.SiteUser;
import com.example.BoardService.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/question") // URL 프리픽스(prefix)
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;


    // view_list
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {

        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    // view_detail
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id, AnswerForm answerForm ) {

        Optional<Question> question = questionService.getQuestion(id);

        if (question.isPresent()) {
            model.addAttribute("question", question.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"entity not found");
        }

        return "question_detail";
    }

    // view_create
    @PreAuthorize("isAuthenticated()") // 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }

    // post_create
    @PreAuthorize("isAuthenticated()") // 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
        // Principal: 현재 로그인한 사용자에 대한 정보를 알기 위해 사용 (스프링 시큐리티가 제공, SecurityConfig 에서 설정)
        // principal 객체는 로그인을 해야만 생성되는 객체
        // principal.getName(): 현재 로그인한 사용자의 이름

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Optional<SiteUser> user = userService.getUser(principal.getName());

        if (user.isPresent()) {
            questionService.create(questionForm.getSubject(), questionForm.getContent(), user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
        }
        return "redirect:/question/list";
    }

    // view_Modify
    @PreAuthorize("isAuthenticated()") // 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    @GetMapping("/modify/{id}")
    public String questionModify(@PathVariable Integer id, Principal principal, QuestionForm questionForm){

        Optional<Question> oQuestion = questionService.getQuestion(id);

        if (oQuestion.isPresent()) {
            Question question = oQuestion.get();
            // 현재 로그인한 사용자와 질문의 작성자가 동일하지 않을 경우
            if(!question.getAuthor().getUsername().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "access denied");
            }
            // 클라가 전달해준 값 setter
            questionForm.setSubject(question.getSubject());
            questionForm.setContent(question.getContent());
        }

        return "question_form";
    }

    // post_Modify
    @PreAuthorize("isAuthenticated()") // 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @PathVariable Integer id, Principal principal){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Optional<Question> oQuestion = questionService.getQuestion(id);

        if(oQuestion.isPresent()) {
            Question question = oQuestion.get();

            // 현재 로그인한 사용자와 질문의 작성자가 동일하지 않을 경우
            if(!question.getAuthor().getUsername().equals(principal.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "access denied");
            }
            // 수정 로직 발동
            questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "data not found");
        }
        return String.format("redirect:/question/detail/%s", id);
    }
}
