package com.telefield.onebody.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    POWER_OFF("전원 차단"),

    NON_RECEIVE("미수신"),
    
    TOO_MUCH_CO2("이산화탄소 과다");

    private final String description;
}
