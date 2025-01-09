package com.example.mss.application.category.dto;

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
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Status stauts;
    private Instant crtDt;
    private Instant updDt;
}
