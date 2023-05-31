package com.telefield.onebody.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserInfoDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private String gender;
        @NotNull
        private String location;
        @NotNull
        private String username;
        @NotNull
        private LocalDate birthdate;
    }
}
