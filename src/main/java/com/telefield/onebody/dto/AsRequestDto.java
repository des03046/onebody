package com.telefield.onebody.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsRequestDto {

    private String userId;

    private String reason;
}
