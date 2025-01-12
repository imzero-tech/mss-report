package com.example.mss.application.product.web;

import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.common.dto.CrudCd;
import com.example.mss.application.common.dto.Status;
import com.example.mss.application.product.dto.CrudRequest;
import com.example.mss.application.product.dto.TaskCd;
import com.example.mss.application.product.service.ProductsService;
import com.example.mss.infrastructure.constants.RETURN_TP;
import com.example.mss.infrastructure.entity.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;

import static com.example.mss.application.util.MssClassUtil.isValidEnum;

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
    private final BrandService brandService;
    private final ResourceHttpRequestHandler staticResourceRequestHandler;

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
    public ResponseBase<Object> totalLowPrice() {
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
    public ResponseBase<Object> minMaxPrice(
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

    /**
     *  브랜드 및 상품을 추가/업데이트/삭제 하는 API
     *  - 요청 : Request Body JSON
     *
     */
    @PostMapping("/xpi/v1/mss/{crudType}/{task}")
    public ResponseBase<Object> entityCrud(
            @PathVariable  String crudType,
            @PathVariable String task,
            @RequestBody CrudRequest cRequest) {

        // crudType 항목 체크
        if (!isValidEnum(CrudCd.class, crudType))
            return ResponseBase.of(RETURN_TP.FAIL, "invalid crud type: " + crudType);

        // task 항목 체크
        if (!isValidEnum(TaskCd.class, task))
            return ResponseBase.of(RETURN_TP.FAIL, "invalid task type: " + crudType);

        return performEntityCrud(CrudCd.valueOf(crudType), task, cRequest);
    }

    // 항목에 따른 crud 작업
    private ResponseBase<Object> performEntityCrud(CrudCd crudCd, String task, CrudRequest cRequest) {
        // check crdType
        switch (crudCd) {
            case add -> { return entityCrudAdd(TaskCd.valueOf(task), cRequest); }
            case fetch -> { return entityCrudFetch(TaskCd.valueOf(task), cRequest); }
            case del -> { return entityCrudDel(TaskCd.valueOf(task), cRequest); }
            default -> {
                return ResponseBase.of(RETURN_TP.FAIL, "Unknow Error : [" + cRequest.toString() + "]");
            }
        }
    }

    // 등록
    private ResponseBase<Object> entityCrudAdd(TaskCd taskCd, CrudRequest cRequest) {
        switch (taskCd) {
            case brand -> { return brandService.resBrandAdd(cRequest); }
            case product -> { return productsService.resProductAdd(cRequest, brandService, categoryService); }
            default -> {
                return ResponseBase.of(RETURN_TP.FAIL, "Unknow Error : [ " + cRequest + "]");
            }
        }
    }

    // 수정
    private ResponseBase<Object> entityCrudFetch(TaskCd taskCd, CrudRequest cRequest) {
        switch (taskCd) {
            case brand -> { return brandService.resBrandUpdate(cRequest); }
            case product -> { return productsService.resProductUpdate(cRequest, brandService, categoryService); }
            default -> {
                return ResponseBase.of(RETURN_TP.FAIL, "Unknow Error : [ " + cRequest + "]");
            }
        }
    }

    // 삭제
    private ResponseBase<Object> entityCrudDel(TaskCd taskCd, CrudRequest cRequest) {
        switch (taskCd) {
            case brand -> {
                cRequest.getBrand().setStatus(Status.DELETE);
                return brandService.resBrandUpdate(cRequest); }
            case product -> { return productsService.resProductDelete(cRequest);}
            default -> {
                return ResponseBase.of(RETURN_TP.FAIL, "Unknow Error : [ " + cRequest + "]");
            }
        }
    }
}
