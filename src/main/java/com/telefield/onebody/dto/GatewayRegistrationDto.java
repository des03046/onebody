package com.telefield.onebody.dto;

import com.telefield.onebody.entity.Gateway;
import lombok.*;

import javax.validation.constraints.NotNull;

public class GatewayRegistrationDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private String macAddress;
        @NotNull
        private String userId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Response {
        private String userId;
        private String macAddress;

        public static Response fromEntity(Gateway gateway) {
            return Response.builder()
                    .macAddress(gateway.getGwMacAddress())
                    .build();
        }
    }
}
