package com.example.BoardService.user;

import lombok.Getter;

// 인증후에 사용자에게 부여할 권한
@Getter // 상수 자료형이므로 setter 는 사용하지 않는다.
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
// 사람이 작성한 질문이나 답변을 ADMIN 권한을 지닌 사용자는 수정이 가능하도록