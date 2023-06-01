package com.telefield.onebody.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStateType {
    AS_REQUEST("as 신청"),

    DEVICE_RECALL("기기 회수"),

    REPAIRING("수리 중"),

    AS_COMPLETED("수리 완료"),

    REINSTALL("재설치");

    private final String description;
}
