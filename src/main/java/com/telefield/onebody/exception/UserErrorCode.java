package com.telefield.onebody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode {
    NO_USER("해당되는 유저가 없습니다."),
    NOT_ALLOWED_USER("허가된 유저가 없습니다."),
    ALREADY_ALLOWED_USER("이미 허가된 아이디입니다."),
    ALREADY_REGISTERED_USER("이미 가입된 아이디입니다.");

    private final String message;
}
