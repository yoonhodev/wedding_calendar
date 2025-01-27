package com.example.wedding_calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dress_tour")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DressTourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "customer_idx", nullable = false)
    private CustomerEntity customer;

    @Column(name = "d_day", nullable = false)
    private LocalDate dDay;

    @Column(name = "guide_31days", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer guide31Days;

    @Column(name = "guide_14days", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer guide14Days;

    @Column(name = "guide_2days", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer guide2Days;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private String createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private String updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = java.time.LocalDateTime.now().toString();
        this.updatedAt = java.time.LocalDateTime.now().toString();
    }
}
