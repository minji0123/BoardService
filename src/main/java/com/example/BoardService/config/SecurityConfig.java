package com.example.BoardService.config;

import com.example.BoardService.user.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 로그인 처리 담당 service 추가
    @Autowired
    private UserSecurityService userSecurityService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll()
        // /h2-console 로 이동 시, **CSRF** 오류를 방지하기 위해 설정
        .and()
        .csrf().ignoringAntMatchers("/h2-console/**")
        // X-Frame-Options 헤더를 설정하여 프레임을 감추는 것을 방지한다.
        .and()
        .headers()
        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        // 로그인 설정
        .and()
        .formLogin()
        .loginPage("/user/login")
        .defaultSuccessUrl("/question/list")
        // 로그아웃 설정
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        .logoutSuccessUrl("/question/list")
        .invalidateHttpSession(true)
        ;
    }

    // 암호화를 위해 시큐리티의 BCryptPasswordEncoder 클래스를 사용하여 암호화하여 비밀번호를 저장
    // BCryptPasswordEncoder는 BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화한다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 암호화 방식에 유연한 PasswordEncoder 인터페이스를 빈(bean)으로 등록해서 사용

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // AuthenticationManagerBuilder 객체 auth는 스프링 시큐리티의 인증을 담당

        // auth 객체에 사용자 조회를 UserSecurityService가 담당하도록 설정
        // 비밀번호 검증에 사용할 passwordEncoder도 함께 등록해야 한다.
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }


}
/*
CSRF란?
CSRF(cross site request forgery)는 웹 사이트 취약점 공격을 방지를 위해 사용하는 기술이다.
스프링 시큐리티가 CSRF 토큰 값을 세션을 통해 발행하고 웹 페이지에서는 폼 전송시에 해당 토큰을 함께 전송하여
실제 웹 페이지에서 작성된 데이터가 전달되는지를 검증하는 기술이다.
 */