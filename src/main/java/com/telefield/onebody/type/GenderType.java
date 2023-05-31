package com.telefield.onebody.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {
    MAN("남"),

    WOMAN("여");

    private final String description;
}
