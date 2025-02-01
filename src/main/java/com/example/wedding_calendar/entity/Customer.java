package com.example.wedding_calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "husband_name")
    private String husbandName;

    @Column(name = "wife_name")
    private String wifeName;

    @Column(name = "makeup_rehearsal")
    private String makeupRehearsal;

    @Column(name = "makeup_wedding")
    private String makeupWedding;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events;

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
