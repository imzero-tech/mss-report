package com.example.mss.application.product.web;

import com.example.mss.application.product.dto.ProductLowPrice;
import com.example.mss.application.product.service.ProductsService;
import com.example.mss.infrastructure.constants.RETURN_TP;
import com.example.mss.infrastructure.entity.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

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

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     * @return
     */
    @GetMapping(value = "/xpi/v1/mss/lowest-price")
    public ResponseBase<Map<String, Object>> lowPrice() {
        ResponseBase<Map<String, Object>> rtnResponse = new ResponseBase<>();

        try {
            var lowPriceList = productsService.lowestPriceList();
            double lowPriceListSum = lowPriceList.stream().mapToDouble(ProductLowPrice::getPrice).sum();

            log.info("Low Price List: {}", (int) lowPriceListSum);

            rtnResponse.setCode(RETURN_TP.OK);
            rtnResponse.setMessage(RETURN_TP.OK.getMessage());
            rtnResponse.setData(Map.of("value", lowPriceList, "total", (int) lowPriceListSum));
            return rtnResponse;
        } catch (Exception e) {
            log.debug(e.getMessage(), e);

            rtnResponse.setCode(RETURN_TP.FAIL);
            rtnResponse.setMessage(e.getMessage());
            return rtnResponse;
        }
    }

    /**
     * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     * @return
     */
    @GetMapping(value = "/xpi/v1/mss/total-lowest-price")
    public ResponseBase<Map<String, Object>> totalLowPrice() {
        ResponseBase<Map<String, Object>> rtnResponse = new ResponseBase<>();

        try {
            var brandLowPriceList = productsService.brandLowestPriceList();
            Optional<String> first = brandLowPriceList.stream().map(ProductLowPrice::getBrandName).findFirst();
            double brandLowPriceListSum = brandLowPriceList.stream().mapToDouble(ProductLowPrice::getPrice).sum();

            log.info("Low Price List: {}", (int) brandLowPriceListSum);

            var categoris = brandLowPriceList.stream()
                    .map(product -> Map.of(
                            "productNm", product.getBrandName(),
                            "price", product.getPrice()
                    ))
                    .toList();

            var minBrandValue = Map.of("brand", first.orElse("")
                    , "category", categoris
                    , "total", (int) brandLowPriceListSum);

            rtnResponse.setCode(RETURN_TP.OK);
            rtnResponse.setMessage(RETURN_TP.OK.getMessage());
            rtnResponse.setData(Map.of("value", minBrandValue));
            return rtnResponse;
        } catch (Exception e) {
            log.debug(e.getMessage(), e);

            rtnResponse.setCode(RETURN_TP.FAIL);
            rtnResponse.setMessage(e.getMessage());
            return rtnResponse;
        }
    }
}
