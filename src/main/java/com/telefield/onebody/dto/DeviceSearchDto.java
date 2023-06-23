package com.telefield.onebody.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceSearchDto {
    private String gwMacAddress;
    private String gwPhone;
    private String powerState;
    private String userName;
}
