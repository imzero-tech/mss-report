package com.example.mss.application.product.dto;

import com.example.mss.application.common.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDto {
    private Long productId;
    private String productName;
    private Long categoryId;
    private Long brandId;
    private Integer price;
    private Integer salePrice;
    private Status status;
    private Instant crtDt;
    private Instant updDt;
}
