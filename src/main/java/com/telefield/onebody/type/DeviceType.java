package com.telefield.onebody.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {
    ACTIVITY_SENSOR("활동 센서"),

    RADAR_SENSOR("레이더 센서");

    private final String description;
}
