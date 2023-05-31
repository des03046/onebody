package com.telefield.onebody.dto;

import com.telefield.onebody.entity.Gateway;
import com.telefield.onebody.type.DeviceType;
import com.telefield.onebody.type.GatewayStateType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatewayDetailDto {
    private String gwMacAddress;

    private String gwPhone;

    private DeviceType gwDevType;

    private String gwVer;

    private String gwRssi;

    private GatewayStateType powerState;

    private LocalDateTime regDate;

    public static GatewayDetailDto fromEntity(Gateway gateway) {
        return GatewayDetailDto.builder()
                .gwMacAddress(gateway.getGwMacAddress())
                .gwPhone(gateway.getGwPhone())
                .gwDevType(gateway.getGwDevType())
                .powerState(gateway.getPowerState())
                .gwVer(gateway.getGwVer())
                .gwRssi(gateway.getGwRssi())
                .regDate(gateway.getRegDate())
                .build();
    }
}
