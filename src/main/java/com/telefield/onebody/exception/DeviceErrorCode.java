package com.telefield.onebody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceErrorCode {
    NO_DEVICE("해당되는 기기가 없습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    DEVICE_ALREADY_ENROLLED("이미 등록된 기기입니다.");


    private final String message;
}
