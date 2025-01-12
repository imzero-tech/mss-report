package com.example.mss.application.category.entity;

import com.example.mss.application.common.dto.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * packageName  : com.example.mss.application.category.entity
 * fileName     : Category
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Entity(name = "CATEGORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 2115790726796147140L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column
    private String categoryName;
    @Column
    private String categoryDesc;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(updatable = false)
    private Instant crtDt;
    @Column
    private Instant updDt;

    @PrePersist
    protected void onCreate() {
        status = Status.OK;
        crtDt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updDt = Instant.now();
    }

//    /**
//     * 조인 관계 설정
//     */
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private List<Products> Products = new ArrayList<>();
}
