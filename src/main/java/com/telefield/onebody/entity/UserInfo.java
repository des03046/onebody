package com.telefield.onebody.entity;

import com.telefield.onebody.type.GenderType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

//디바이스 사용자 정보
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DETAIL_INFO")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;

    private String userName;

    private String location;

    private GenderType gender;

    private LocalDate birthDate;

    private String gwMacAddress;

    private String gwPhone;
}
