package com.example.mss.application.admin.dto;

import com.example.mss.application.brand.dto.BrandDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * packageName  : com.example.mss.application.admin.dto
 * fileName     : AdminBrandDto
 * auther       : imzero-tech
 * date         : 2025. 1. 13.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 13.      imzero-tech             최초생성
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AdminBrandDto extends BrandDto {
    private String companyName;
}
