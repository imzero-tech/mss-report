package com.example.mss.application.brand.service;

import com.example.mss.application.brand.dao.BrandDao;
import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.common.dto.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName  : com.example.mss.application.brand.service
 * fileName     : BrandService
 * auther       : zero
 * date         : 2025. 1. 10.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 10.      zero             최초생성
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandDao brandDao;
    private final ModelMapper modelMapper;

    @Value("${spring.config.activate.on-profile:default}")
    private String onProfile;

    public List<BrandDto> saveInitBrands(List<BrandDto> brandDtos) {
        if (!Constants.INIT_PROFILE_TEST.getCode().equals(onProfile))
            return null;
        // saveAll : 하나의 connection 으로 다수의 dml 질의
        // ㄴ. insert bulk가 효과적
        return brandDao
                .saveAll(
                        brandDtos.stream()
                                .map(o -> modelMapper.map(o, Brand.class))
                                .toList())
                .stream()
                .map(dest -> modelMapper.map(dest, BrandDto.class))
                .toList();
    }

    public List<BrandDto> getBrandByStatus(Status status) {
        return brandDao.findByStatus(status)
                .stream()
                .map(dest -> modelMapper.map(dest, BrandDto.class))
                .toList();
    }

}
