package com.telefield.onebody.entity;

import com.telefield.onebody.type.DeviceType;
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
@Table(name = "GATEWAY_CYCLE_HISTORY")
public class GatewayCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String gwMacAddress;

    private String gwPhone;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DeviceType gwDevType;

    @NotNull
    private String gwVer;

    @NotNull
    private String gwRssi;

    @NotNull
    private LocalDateTime regDate;
}
