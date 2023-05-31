package com.telefield.onebody.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceDataErrorCode {
    NO_DATA("기기에 기록된 데이터가 없습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다");

    private final String message;
}
