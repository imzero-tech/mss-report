package com.example.mss.application.category.dto;

import com.example.mss.application.common.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName  : com.example.mss.application.category.dto
 * fileName     : CategoryDto
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String categoryDesc;
    private Status status;
    private Instant crtDt;
    private Instant updDt;
}
