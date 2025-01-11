package com.example.mss.application.product.dto;

import com.example.mss.application.common.dto.Status;
import lombok.Builder;
import lombok.Data;

/**
 * packageName  : com.example.mss.application.product.dto
 * fileName     : ProductTutle
 * auther       : imzero-tech
 * date         : 2025. 1. 11.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 11.      imzero-tech             최초생성
 */
@Data
@Builder
public class ProductLowPrice {
    private Long productId;
    private String productName;
    private String brandName;
    private Integer price;
    private Status status;
}
