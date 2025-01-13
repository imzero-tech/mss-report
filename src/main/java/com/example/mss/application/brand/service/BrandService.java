package com.example.mss.application.brand.service;

import com.example.mss.application.brand.dao.BrandDao;
import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.common.dto.Status;
import com.example.mss.application.product.dto.CrudRequest;
import com.example.mss.infrastructure.constants.RETURN_TP;
import com.example.mss.infrastructure.entity.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.mss.application.common.dto.Status.*;
import static com.example.mss.application.util.MssClassUtil.copyNonNullProperties;

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

    public BrandDto saveItem(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        Brand fBrand = brandDao.saveAndFlush(brand);
        return modelMapper.map(fBrand, BrandDto.class);
    }

    public List<BrandDto> getBrandAll() {
        return brandDao.findByStatusIn(List.of(OK, READY, BLIND, STOP, DELETE))
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

    public BrandDto getBrandById(Long id) {
        Brand fBrand = brandDao.findBrandByBrandId(id);
        return null == fBrand? null: modelMapper.map(fBrand, BrandDto.class);
    }

    public BrandDto getBrandByName(String brandName) {
        Brand fBrand = brandDao.findBrandByBrandName(brandName);
        return null == fBrand? null: modelMapper.map(fBrand, BrandDto.class);
    }

    // CrudRequest.Brand 정보를 전달 받아 저장 한 후 ResponseBase<Object> 로 리턴
    public ResponseBase<Object> resBrandAdd(CrudRequest cRequest) {
        if (cRequest.getBrand() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요..[brand null]");

        var brandDto = BrandDto.builder()
                        .companyId(1L)
                        .brandName(cRequest.getBrand().getBrandName())
                        .status(Status.OK)
                        .build();
        try {
            var sBrandDto = this.saveItem(brandDto);
            return ResponseBase.of(RETURN_TP.OK, sBrandDto);
        } catch(Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    // CrudRequest.Brand 정보를 전달 받아 업데이트 한 후 ResponseBase<Object> 로 리턴
    public ResponseBase<Object> resBrandUpdate(CrudRequest cRequest) {
        if (cRequest.getBrand() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요..[brand null]");

        // 요청 정보가 있는지 체크
        BrandDto fBrandDto = this.getBrandById(cRequest.getBrand().getBrandId());
        if (fBrandDto == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요..[find brand empty]");

        // 업데이트할 정보를 셋팅
        var nBrandDto = BrandDto.builder()
                .brandId(cRequest.getBrand().getBrandId())
                .companyId(1L)
                .brandName(cRequest.getBrand().getBrandName())
                .status(cRequest.getBrand().getStatus())
                .build();

        // 업데이트할 정보를 요청 정보로 복사 - null 은 제외 처리
        copyNonNullProperties(nBrandDto, fBrandDto);

        try {
            // 업데이트된 요청 정ㅂ를 최종 업데이트
            var sBrandDto = this.saveItem(fBrandDto);
            return ResponseBase.of(RETURN_TP.OK, sBrandDto);
        } catch(Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }
}
