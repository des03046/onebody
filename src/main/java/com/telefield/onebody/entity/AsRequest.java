package com.telefield.onebody.entity;

import com.telefield.onebody.type.RequestStateType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AS_REQUEST_HISTORY")
public class AsRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    private String requestUser;

    private String macAddress;

    private String phone;

    private RequestStateType state;

    private String reason;

    @CreationTimestamp
    private LocalDateTime regDate;

    @Nullable
    private LocalDateTime completedDate;
    @Nullable
    private String location;
    @Nullable
    private String userName;
}
