package com.example.mss.application.company.service;

import com.example.mss.application.company.dao.CompanyDao;
import com.example.mss.application.company.dto.CompanyDto;
import com.example.mss.application.company.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyDao companyDao;
    private final ModelMapper modelMapper;

    public List<CompanyDto> saveInitCompanies(List<CompanyDto> companyDtos) {
        return companyDao
                .saveAll(
                    companyDtos.stream()
                            .map(o -> modelMapper.map(o, Company.class))
                            .toList())
                .stream()
                .map(dest -> modelMapper.map(dest, CompanyDto.class))
                .toList();
    }
}
