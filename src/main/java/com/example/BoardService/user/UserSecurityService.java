package com.example.BoardService.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
// 스프링 시큐리티 로그인 처리
public class UserSecurityService implements UserDetailsService {

    // UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
    // loadUserByUsername 메서드는 사용자명으로 비밀번호를 조회하여 리턴하는 메서드
    // SpringSecurity 는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가 화면으로부터 입력 받은 비밀번호와 일치하는지를 검사하는 로직을 내부적으로 가지고 있다.
    @Autowired
    private UserRepository userRepository;

    // 유저 정보 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자명으로 사용자 정보를 조회
        Optional<SiteUser> _siteUser = userRepository.findByusername(username);

        // 조회된 사용자 정보가 없으면 예외를 발생시킴
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // 사용자 정보를 조회한 결과를 siteUser 객체로 변환
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자명이 "admin"인 경우에는 ADMIN 권한을 부여하고 그 이외의 경우에는 USER 권한을 부여
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }


}
