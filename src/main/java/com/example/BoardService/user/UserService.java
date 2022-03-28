package com.example.BoardService.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 유저 생성
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);

// BCryptPasswordEncoder 객체를 생성하는 대신, 암호화 방식에 유연한 PasswordEncoder 인터페이스를 빈(bean)으로 등록해서 사용
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    // 유저 조회
    public Optional<SiteUser> getUser(String username) {
        return this.userRepository.findByusername(username);
    }

}
