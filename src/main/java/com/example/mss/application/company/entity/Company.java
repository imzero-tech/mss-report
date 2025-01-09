package com.example.mss.application.company.entity;

import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.common.dto.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName  : com.example.mss.application.company.entity
 * fileName     : Company
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Entity(name = "COMPANY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = -4187671076512337006L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    @Column
    private String companyName;
    @Enumerated(EnumType.STRING)
    private Status stauts;
    @Column
    private Instant crtDt;
    @Column
    private Instant updDt;

    @PrePersist
    protected void onCreate() {
        crtDt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updDt = Instant.now();
    }


    /**
     * 조인 관계 설정
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Brand> brands = new ArrayList<>();
}
