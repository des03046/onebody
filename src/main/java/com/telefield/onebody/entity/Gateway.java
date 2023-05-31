package com.telefield.onebody.entity;

import com.telefield.onebody.type.DeviceType;
import com.telefield.onebody.type.GatewayStateType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

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
@Table(name = "GATEWAY_STATUS")
@EntityListeners(AuditingEntityListener.class)
public class Gateway {

    @Id
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

    @Enumerated(EnumType.STRING)
    @NotNull
    private GatewayStateType powerState;

    @Nullable
    private String managerId;

    @Nullable
    private String childId;

    @Nullable
    private String userName;

    @Nullable
    private String location;

    @CreatedDate
    private LocalDateTime createdAt;
}
