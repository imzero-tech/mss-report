package com.example.mss.application.company.dto;

import com.example.mss.application.common.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * packageName  : com.example.mss.application.company.dto
 * fileName     : CompanyDto
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
public class CompanyDto {
    private Long companyId;
    private String companyName;
    private Status status;
    private Instant crtDt;
    private Instant updDt;
}
