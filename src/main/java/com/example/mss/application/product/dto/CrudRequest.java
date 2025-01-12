package com.example.mss.application.product.dto;

import com.example.mss.application.brand.dto.BrandDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

/**
 * packageName  : com.example.mss.application.product.dto
 * fileName     : CrudRequest
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
public class CrudRequest {
    private BrandDto brand;
    private ProductItem product;
}
