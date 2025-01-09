package com.example.mss.application.brand.entitiy;

import com.example.mss.application.common.dto.Status;
import com.example.mss.application.company.entity.Company;
import com.example.mss.application.product.entity.Products;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName  : com.example.mss.application.brand.entitiy
 * fileName     : Brand
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Entity(name = "BRAND")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Brand implements Serializable {

    @Serial
    private static final long serialVersionUID = -5734912605416710088L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;
    @Column
    private String brandName;
    @Column
    private Long companyId;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_companyId")
    private Company company;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Products> products = new ArrayList<>();
}
