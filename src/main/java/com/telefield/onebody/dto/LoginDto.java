package com.telefield.onebody.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginDto {
    @NotNull
    private String userId;
    @NotNull
    private String password;
}
