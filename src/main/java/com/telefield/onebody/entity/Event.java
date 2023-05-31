package com.telefield.onebody.entity;

import com.telefield.onebody.type.EventType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT_HISTORY")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long eventId;

    @Column(length = 25)
    private String gwPhone;

    @Column(length = 25)
    private String gwMacAddress;

    private EventType eventType;

    @CreationTimestamp
    private LocalDateTime regDate;
}
