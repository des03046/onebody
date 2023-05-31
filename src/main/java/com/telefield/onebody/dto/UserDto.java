package com.telefield.onebody.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private String userId;
        @NotNull
        private String password;
        @NotNull
        private String location;
        @NotNull
        private String username;
        @NotNull
        private String phoneNumber;
    }


}
