package com.example.mss.application.product.service;

import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.product.dao.ProductsDao;
import com.example.mss.application.product.dto.ProductsDto;
import com.example.mss.application.product.entity.Products;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
public class ProductsService {

    private final ModelMapper modelMapper;
    private final ProductsDao productsDao;

    @Value("${spring.config.activate.on-profile:default}")
    private String onProfile;

    public List<ProductsDto> saveInitProducts(List<ProductsDto> productsDtos) {
        if (!Constants.INIT_PROFILE_TEST.getCode().equals(onProfile))
            return null;

        // saveAll : 하나의 connection 으로 다수의 dml 질의
        // ㄴ. insert bulk가 효과적
        return productsDao
                .saveAll(
                        productsDtos.stream()
                                .map(o -> modelMapper.map(o, Products.class))
                                .toList())
                .stream()
                .map(dest -> modelMapper.map(dest, ProductsDto.class))
                .toList();
    }
}
