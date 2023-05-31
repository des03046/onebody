package com.telefield.onebody.dto;

import com.telefield.onebody.entity.GatewayData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatewayDataDto {
    private String temperature;
    private String humidity;
    private String lux;
    private String co2;
    private String tVoc;
    private LocalDateTime regDate;

    public static GatewayDataDto fromEntity(GatewayData gatewayData) {
        return GatewayDataDto.builder()
                .temperature(gatewayData.getTemperature())
                .humidity(gatewayData.getHumidity())
                .lux(gatewayData.getLux())
                .co2(gatewayData.getCo2())
                .tVoc(gatewayData.getTVoc())
                .regDate(gatewayData.getRegDate())
                .build();
    }
}
