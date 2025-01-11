package com.example.mss.application.product.service;

import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.product.dao.ProductsDao;
import com.example.mss.application.product.dto.ProductLowPrice;
import com.example.mss.application.product.dto.ProductsDto;
import com.example.mss.application.product.entity.Products;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName  : com.example.mss.application.product.service
 * fileName     : ProductsService
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

    public List<ProductLowPrice> lowestPriceList() {
        List<Tuple> tuples = productsDao.findAllMinPriceLeftFetchJoin();
        log.info("allMinPriceLeftFetchJoin: {}", tuples);

        return tuples.stream()
                .map(o -> ProductLowPrice.builder()
                        .productId(o.get(0, Long.class))
                        .productName(o.get(1, String.class))
                        .brandName(o.get(2,String.class))
                        .price(o.get(3, Integer.class))
                        .build())
                .toList();
    }
}
