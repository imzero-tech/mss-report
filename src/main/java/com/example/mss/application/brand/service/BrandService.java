package com.example.mss.application.brand.service;

import com.example.mss.application.brand.dao.BrandDao;
import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.entitiy.Brand;
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
public class BrandService {
    private final BrandDao brandDao;
    private final ModelMapper modelMapper;

    public List<BrandDto> saveBrands(List<BrandDto> brandDtos) {
        return brandDao
                .saveAll(
                        brandDtos.stream()
                                .map(o -> Brand.builder()
                                        .brandName(o.getBrandName())
                                        .companyId(o.getCompanyId())
                                        .stauts(o.getStauts())
                                        .build())
                                .toList())
                .stream()
                .map(dest -> modelMapper.map(dest, BrandDto.class))
                .toList();
    }

}
