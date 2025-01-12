package com.example.mss.application.product.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

/**
 * packageName  : com.example.mss.application.product.dto
 * fileName     : ProductItem
 * auther       : imzero-tech
 * date         : 2025. 1. 12.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 12.      imzero-tech             최초생성
 */
@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductItem {
    private Long productId;
    private String productName;
    private String brandName;
    private String categoryName;
    private Integer price;
}
