package com.example.mss.application.product.web;

import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.product.service.ProductsService;
import com.example.mss.infrastructure.constants.RETURN_TP;
import com.example.mss.infrastructure.entity.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * packageName  : com.example.mss.application.product.web
 * fileName     : ProductRestController
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductsService productsService;
    private final CategoryService categoryService;

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     * @return ResponseBase<Map<String, Object>>
     */
    @GetMapping(value = "/xpi/v1/mss/lowest-price")
    public ResponseBase<Object> lowPrice() {
        try {
            return ResponseBase.of(RETURN_TP.OK, productsService.lowestPriceList());
        } catch (Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    /**
     * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     * @return ResponseBase<Map<String, Object>>
     */
    @GetMapping(value = "/xpi/v1/mss/total-lowest-price")
    public ResponseBase<Map<String, Object>> totalLowPrice() {
        try {
            return ResponseBase.of(RETURN_TP.OK, productsService.brandLowestPriceList());
        } catch (Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     * @return ResponseBase<Map<String, Object>>
     */
    @GetMapping(value = "/xpi/v1/mss/min-max-price")
    public ResponseBase<Map<String, Object>> minMaxPrice(
            @RequestParam("category") String categoryDesc
    ) {
        // categoryDesc 입력 여부 체크
        List<CategoryDto> categories = categoryService.getCategoriesByCategoryDesc(categoryDesc);

        if (categories.isEmpty())
            return ResponseBase.of(RETURN_TP.FAIL, "category is Empty : [" + categoryDesc + "]");

        try {
            return ResponseBase.of(RETURN_TP.OK, productsService.brandMinMaxPriceList(categories.get(0)));
        } catch (Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }
}
