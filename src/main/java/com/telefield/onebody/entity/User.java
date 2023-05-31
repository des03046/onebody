package com.telefield.onebody.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

//로그인정보
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "USER_INFO")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;

    @NonNull
    private String userId;

    @NonNull
    private String password;

    private String userName;

    private String phoneNumber;

    private String location;

    private boolean management;

    private boolean allowed;

    @LastModifiedDate
    private LocalDateTime lastLogin;

    @CreationTimestamp
    private LocalDateTime creationAt;
}
