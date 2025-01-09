package com.example.mss.application.company.dto;

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
public class CompanyDto {
    private Long companyId;
    private String companyName;
    private Status stauts;
    private Instant crtDt;
    private Instant updDt;
}
