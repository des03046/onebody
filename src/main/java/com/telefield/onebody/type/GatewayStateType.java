package com.telefield.onebody.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GatewayStateType {

    NORMAL("정상"),

    POWER_OFF("전원 차단"),

    AS_REQUEST("as 신청"),

    NON_RECEIVE("미수신");

    private final String description;
}
