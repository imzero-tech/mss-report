package com.example.mss.application.product.entity;

import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.category.entity.Category;
import com.example.mss.application.common.dto.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * packageName  : com.example.mss.application.product.entity
 * fileName     : Products
 * auther       : zero
 * date         : 2025. 1. 8.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 8.      zero             최초생성
 */
@Entity(name = "PRODUCTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Products implements Serializable {
    @Serial
    private static final long serialVersionUID = -1966341663304045466L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column
    private String productName;
    @Column
    private Integer categoryId;
    @Column
    private Integer brandId;
    @Column
    private Integer price;
    @Column
    private Integer salePrice;
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
    @JoinColumn(name = "products_categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_brandId")
    private Brand brand;
}
