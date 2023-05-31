package com.telefield.onebody.dto;

import com.telefield.onebody.entity.Gateway;
import com.telefield.onebody.type.GatewayStateType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatewayDto {
    private String gwPhone;
    private String gwMacAddress;
    private GatewayStateType powerState;
    private String userName;
    private String location;

    public static GatewayDto fromEntity(Gateway gateway) {
        return GatewayDto.builder()
                .gwMacAddress(gateway.getGwMacAddress())
                .powerState(gateway.getPowerState())
                .location(gateway.getLocation())
                .userName(gateway.getUserName())
                .gwPhone(gateway.getGwPhone())
                .build();
    }

}
