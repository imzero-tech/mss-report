package com.example.mss.application.product.dto;

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
public class ProductMinMaxPrice {
    private String minMax;
    private String brandName;
    private Integer price;
}
