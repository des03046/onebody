package com.telefield.onebody.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsRequestSearchDto {
    private LocalDateTime regDate;
    private String state;
    private String phone;
    private String userName;
    private String macAddress;
    private LocalDateTime completedDate;
}
