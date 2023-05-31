package com.telefield.onebody.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCountDto {

    private LocalDateTime date;
    private long count;
}
