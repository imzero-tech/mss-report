package com.example.mss.application.product.service;

import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.common.dto.Status;
import com.example.mss.application.product.dao.ProductsDao;
import com.example.mss.application.product.dto.ProductLowPrice;
import com.example.mss.application.product.dto.ProductMinMaxPrice;
import com.example.mss.application.product.dto.ProductsDto;
import com.example.mss.application.product.entity.Products;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public ProductsDto saveItem(ProductsDto productsDto) {
        Products products = modelMapper.map(productsDto, Products.class);
        Products fproducts = productsDao.saveAndFlush(products);
        return modelMapper.map(fproducts, ProductsDto.class);
    }

    public Map<String, Object> lowestPriceList() {
        List<Tuple> tuples = productsDao.findAllMinPriceFetchJoin();
        log.info("allMinPriceLeftFetchJoin: {}", tuples);

        var lowPriceList = tuples.stream()
                .map(o -> ProductLowPrice.builder()
                        .productId(o.get(0, Long.class))
                        .productName(o.get(1, String.class))
                        .brandName(o.get(2,String.class))
                        .price(o.get(3, Integer.class))
                        .build())
                .toList();

        double lowPriceListSum = lowPriceList.stream().mapToDouble(ProductLowPrice::getPrice).sum();

        return Map.of("value", lowPriceList, "total", (int) lowPriceListSum);
    }

    public Map<String, Object> brandLowestPriceList() {
        List<Tuple> tuples = productsDao.findBrandMinPriceFetchJoin();

        var brandLowPriceList = tuples.stream()
                .map(o -> ProductLowPrice.builder()
                        .price(o.get(0, Integer.class))
                        .productName(o.get(1, String.class))
                        .brandName(o.get(2,String.class))
                        .build())
                .toList();

        double brandLowPriceListSum = brandLowPriceList.stream().mapToDouble(ProductLowPrice::getPrice).sum();

        return Map.of("brand", brandLowPriceList.stream().map(ProductLowPrice::getBrandName).findFirst().orElse("")
                , "category", brandLowPriceList.stream()
                                                    .map(product -> Map.of(
                                                            "productNm", product.getProductName(),
                                                            "price", product.getPrice()))
                                                    .toList()
                , "total", (int) brandLowPriceListSum);
    }

    public Map<String, Object> brandMinMaxPriceList(CategoryDto categoryDto) {
        List<Tuple> tuples = productsDao.findBrandMinMaxFetchJoin(categoryDto);

        var productMinMaxPrices = tuples.stream()
                .map(o -> ProductMinMaxPrice.builder()
                        .minMax(o.get(0, String.class))
                        .brandName(o.get(1, String.class))
                        .price(o.get(2,Integer.class))
                        .build())
                .toList();

        return Map.of("category", categoryDto.getCategoryDesc()
                ,"min-price", productMinMaxPrices.stream().filter(o -> o.getMinMax().equals("min")).findFirst().map(o -> Map.of("brand", o.getBrandName(), "price", o.getPrice()))
                ,"max-price", productMinMaxPrices.stream().filter(o -> o.getMinMax().equals("max")).findFirst().map(o -> Map.of("brand", o.getBrandName(), "price", o.getPrice())));
    }

    public ProductsDto getProductsBYId(Long productsId) {
        Products fProducts = productsDao.findByProductId(productsId);
        return null == fProducts? null: modelMapper.map(fProducts, ProductsDto.class);
    }

    public void setProductsStatusByBrandId(Long brandId, Status status) {
        productsDao.updateRowsByBrandId(brandId, status);
    }

    public void setProductsStatusByProductId(Long productId, Status status) {
        productsDao.updateRowsByProductId(productId, status);
    }
}
