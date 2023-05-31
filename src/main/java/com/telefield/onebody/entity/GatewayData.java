package com.telefield.onebody.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GATEWAY_DATA_STATUS")
public class GatewayData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String gwMacAddr;

    private String gwPhone;

    @NotNull
    private String gwRssi;

    @NotNull
    private LocalDateTime regDate;

    @NotNull
    private String temperature;

    @NotNull
    private String humidity;

    @NotNull
    private String lux;

    @NotNull
    private String co2;

    @NotNull
    private String tVoc;
}
