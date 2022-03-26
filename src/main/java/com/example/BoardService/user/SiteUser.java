package com.example.BoardService.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SiteUser { // 스프링 시큐리티에 이미 User 클래스가 있어서 이름을 바꿔준다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 유일한 값만 저장
    private String username;

    @Column(unique = true) // 유일한 값만 저장
    private String email;

    private String password;

}
