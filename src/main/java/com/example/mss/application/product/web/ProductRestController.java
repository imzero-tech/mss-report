package com.example.mss.application.product.web;

import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.common.dto.CrudCd;
import com.example.mss.application.common.dto.Status;
import com.example.mss.application.product.dto.ProductRequest;
import com.example.mss.application.product.dto.ProductResponse;
import com.example.mss.application.product.dto.ProductsDto;
import com.example.mss.application.product.service.ProductsService;
import com.example.mss.infrastructure.constants.RETURN_TP;
import com.example.mss.infrastructure.entity.ResponseBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
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

    public ResponseBase<Object> checkExecute(ProductRequest pRequest) {
        switch (pRequest.getCrudCd()) {
            case CREATE -> { return this.entityAdd(pRequest);}
            case UPDATE -> { return this.entityUpdate(pRequest);}
            case DELETE -> { return this.entityDelete(pRequest);}
            default -> {
                return ResponseBase.of(RETURN_TP.FAIL, "제공하지 않는 CrudCd 입니다. [" + pRequest.getCrudCd() + "]");
            }
        }
    }

    /**
     *  브랜드 및 상품을 추가/업데이트/삭제 하는 API
     *  - 요청 : Request Body JSON
     *  ㄴ. 추가
     *
     */
    @PostMapping("/xpi/v1/mss/entity-excute")
    public ResponseBase<Object> entityAdd(@RequestBody ProductRequest pRequest) {
        // pRequest null 처리
        if (!CrudCd.CREATE.equals(pRequest.getCrudCd())) {
            return checkExecute(pRequest);
        }

        if (pRequest.getBrand() == null && pRequest.getProduct() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요..[request null] [" + pRequest.toString() + "]");
        try {
            // ? 브랜드명이나 상품명이 같을 수 있나?
            var pResponse = ProductResponse.builder()
                                        .crudCd(pRequest.getCrudCd())
                                        .build();
            if (pRequest.getBrand() != null) {
                pRequest.getBrand().setBrandId(null);
                pRequest.getBrand().setCompanyId(1L);
                pRequest.getBrand().setStatus(Status.OK);

                var sBrandDto = brandService.saveItem(pRequest.getBrand());
                pResponse.setBrand(sBrandDto);
            }

            if (pRequest.getProduct() != null) {
                var fnBrand = brandService.getBrandByName(pRequest.getProduct().getBrandName());
                var fnCategory = categoryService.getCategoriesByCategoryDesc(pRequest.getProduct().getCategoryName()).stream().findFirst().orElse(null);

                if (fnBrand == null)
                    fnBrand = brandService.saveItem(BrandDto.builder().brandName(pRequest.getProduct().getBrandName()).companyId(1L).build());

                if (fnCategory == null)
                    fnCategory = categoryService.saveItem(CategoryDto.builder().categoryDesc(pRequest.getProduct().getCategoryName()).categoryName(pRequest.getProduct().getCategoryName()).build());

                var productDto = ProductsDto.builder()
                                        .productId(null)
                                        .productName(pRequest.getProduct().getProductName())
                                        .brandId(fnBrand.getBrandId())
                                        .categoryId(fnCategory.getCategoryId())
                                        .status(Status.OK)
                                        .price(pRequest.getProduct().getPrice()).build();

                var sProductsDto = productsService.saveItem(productDto);
                pResponse.setProduct(sProductsDto);
            }

            return ResponseBase.of(RETURN_TP.OK, Map.of("request", pRequest, "response", pResponse));
        } catch (Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    /**
     *  브랜드 및 상품을 추가/업데이트/삭제 하는 API
     *  - 요청 : Request Body JSON
     *  ㄴ. 업데이트
     *
     */
    @PutMapping("/xpi/v1/mss/entity-excute")
    public ResponseBase<Object> entityUpdate(@RequestBody ProductRequest pRequest) {
        // pRequest null 처리
        if (!CrudCd.UPDATE.equals(pRequest.getCrudCd())) {
            return checkExecute(pRequest);
        }

        if (pRequest.getBrand() == null && pRequest.getProduct() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요.. [" + pRequest.toString() + "]");
        try {
            // 상품 삭제는 괜찮을 것 같은데, 브랜드를 삭제 하면 관련 상품들은 모두 삭제 해야 하는가?
            var pResponse = ProductResponse.builder()
                    .crudCd(pRequest.getCrudCd())
                    .build();

            if (pRequest.getBrand() != null) {
                if (pRequest.getBrand().getBrandId() == null)
                    return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(brand key is null).. [" + pRequest.getBrand() + "]");

                BrandDto fBrandDto = brandService.getBrandById(pRequest.getBrand().getBrandId());

                // 요청 객체를 읽어 들인 객체에 업데이트 한다.
                copyNonNullProperties(pRequest.getBrand(), fBrandDto);

                // 업데이트 본을 저장 요청 한다,
                var sBrandDto = brandService.saveItem(fBrandDto);
                pResponse.setBrand(sBrandDto);
            }

            if (pRequest.getProduct() != null) {
                if (pRequest.getProduct().getProductId() == null)
                    return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(product key is null).. [" + pRequest.getProduct() + "]");

                var fnBrand = brandService.getBrandByName(pRequest.getProduct().getBrandName());
                var fnCategory = categoryService.getCategoriesByCategoryDesc(pRequest.getProduct().getCategoryName()).stream().findFirst().orElse(null);

                // 신규 등록
                if (fnBrand == null)
                    fnBrand = brandService.saveInitBrands(List.of(BrandDto.builder().brandName(pRequest.getProduct().getBrandName()).companyId(1L).build()))
                                            .stream().findFirst().orElse(null);

                if (fnCategory == null)
                    fnCategory = categoryService.saveInitCategories(List.of(CategoryDto.builder().categoryName(pRequest.getProduct().getCategoryName()).build()))
                                            .stream().findFirst().orElse(null);

                if (fnBrand == null || fnCategory == null)
                    return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(브랜드/카테고리 신규등록 실패).. [" + pRequest.getProduct() + "]");

                var productNm = null == pRequest.getProduct().getProductName()? fnCategory.getCategoryDesc(): pRequest.getProduct().getProductName();

                ProductsDto fProductDto = productsService.getProductsBYId(pRequest.getProduct().getProductId());
                ProductsDto nProductDto = ProductsDto.builder()
                                                    .productId(fProductDto.getProductId())
                                                    .brandId(fnBrand.getBrandId())
                                                    .categoryId(fnCategory.getCategoryId())
                                                    .productName(productNm)
                                                    .price(pRequest.getProduct().getPrice())
                                                    .build();

                // 요청 객체를 읽어 들인 객체에 업데이트 한다.
                copyNonNullProperties(nProductDto, fProductDto);

                // 업데이트 본을 저장 요청 한다,
                var sProductsDto = productsService.saveItem(fProductDto);
                pResponse.setProduct(sProductsDto);
            }

            return ResponseBase.of(RETURN_TP.OK, Map.of("request", pRequest, "response", pResponse));
        } catch (Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    /**
     *  브랜드 및 상품을 추가/업데이트/삭제 하는 API
     *  - 요청 : Request Body JSON
     *  ㄴ. 삭제
     *
     */
    @DeleteMapping("/xpi/v1/mss/entity-excute")
    public ResponseBase<Object> entityDelete(@RequestBody ProductRequest pRequest) {
        // pRequest null 처리
        if (!CrudCd.DELETE.equals(pRequest.getCrudCd())) {
            return checkExecute(pRequest);
        }
        if (pRequest.getBrand() == null && pRequest.getProduct() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요.. [" + pRequest.toString() + "]");
        try {
            // 상품 삭제는 괜찮을 것 같은데, 브랜드를 삭제 하면 관련 상품들은 모두 삭제 해야 하는가?
            var pResponse = ProductResponse.builder()
                    .crudCd(pRequest.getCrudCd())
                    .build();
            if (pRequest.getBrand() != null) {
                if (pRequest.getBrand().getBrandId() == null)
                    return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(brand key is null).. [" + pRequest.getBrand() + "]");

                BrandDto fBrandDto = brandService.getBrandById(pRequest.getBrand().getBrandId());
                pResponse.getBrand().setStatus(Status.DELETE);

                // 요청 객체를 읽어 들인 객체에 업데이트 한다.
                copyNonNullProperties(pRequest.getBrand(), fBrandDto);

                // 업데이트 본을 저장 요청 한다,
                var sBrandDto = brandService.saveItem(fBrandDto);
                productsService.setProductsStatusByBrandId(sBrandDto.getBrandId(), sBrandDto.getStatus());
                pResponse.setBrand(sBrandDto);
            }

            if (pRequest.getProduct() != null) {
                if (pRequest.getProduct().getProductId() == null)
                    return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(product key is null).. [" + pRequest.getBrand() + "]");

                // 상태 delete 를 요청 한다,
                productsService.setProductsStatusByProductId(pRequest.getProduct().getProductId(), Status.DELETE);
            }

            return ResponseBase.of(RETURN_TP.OK, Map.of("request", pRequest, "response", pResponse));
        } catch (Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    public static void copyNonNullProperties(Object source, Object target) {
        Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass()))
                .forEach(propertyDescriptor -> {
                    try {
                        Method readMethod = propertyDescriptor.getReadMethod();
                        if (readMethod != null) {
                            Object value = readMethod.invoke(source);
                            if (value != null) {
                                Method writeMethod = propertyDescriptor.getWriteMethod();
                                if (writeMethod != null) {
                                    writeMethod.invoke(target, value);
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.info("copyNonNullProperties error!!", e);
                    }
                });
    }
}
