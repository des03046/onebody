package com.telefield.onebody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventErrorCode {
    NO_EVENT("해당되는 이벤트가 없습니다.");


    private final String message;
}
