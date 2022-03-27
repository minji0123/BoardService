package com.example.BoardService.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // view_signup
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        // 파라미터는 view 페이지에서 th:object="${userCreateForm}" 사용하기 위함
        return "signup_form";
    }

    // post_signup
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

        // 오류발생시 리다이렉트
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        // 비밀번호 일치 확인
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            // 특정 필드의 오류가 아닌 일반적인 오류를 등록할때 사용
            bindingResult.rejectValue("password2", "passwordInCorrect", "패스워드가 일하지 않습니다.");
            return "signup_form";
        }

        // 유저 생성
        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/question/list";
    }

    // view_login
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
    // 실제 로그인을 진행하는 @PostMapping 방식의 메서드는 스프링 시큐리티가 대신 처리하므로 직접 구현할 필요가 없다.
}
