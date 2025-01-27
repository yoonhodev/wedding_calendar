package com.example.wedding_calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private Long idx;

    @Column(name = "id")
    private String id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "created_at", columnDefinition = "TEXT")
    private String createdAt;

    @Column(name = "updated_at", columnDefinition = "TEXT")
    private String updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
