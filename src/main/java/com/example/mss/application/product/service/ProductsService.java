package com.example.mss.application.product.service;

import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.common.dto.Status;
import com.example.mss.application.product.dao.ProductsDao;
import com.example.mss.application.product.dto.CrudRequest;
import com.example.mss.application.product.dto.ProductLowPrice;
import com.example.mss.application.product.dto.ProductMinMaxPrice;
import com.example.mss.application.product.dto.ProductsDto;
import com.example.mss.application.product.entity.Products;
import com.example.mss.infrastructure.constants.RETURN_TP;
import com.example.mss.infrastructure.entity.ResponseBase;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.mss.application.util.MssClassUtil.copyNonNullProperties;

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

    public ResponseBase<Object> resProductAdd(CrudRequest cRequest, BrandService brandService, CategoryService categoryService) {
        if (cRequest.getProduct() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요..[product null]");

        var fnBrand = brandService.getBrandByName(cRequest.getProduct().getBrandName());
        var fnCategory = categoryService.getCategoriesByCategoryDesc(cRequest.getProduct().getCategoryName()).stream().findFirst().orElse(null);

        if (fnBrand == null)
            fnBrand = brandService.saveItem(
                        BrandDto.builder()
                                .brandName(cRequest.getProduct().getBrandName())
                                .companyId(1L)
                                .build());

        if (fnCategory == null)
            fnCategory = categoryService.saveItem(
                        CategoryDto.builder()
                                .categoryDesc(cRequest.getProduct().getCategoryName())
                                .categoryName(cRequest.getProduct().getCategoryName()).build());

        var productDto = ProductsDto.builder()
                .productId(null)
                .productName(cRequest.getProduct().getProductName())
                .brandId(fnBrand.getBrandId())
                .categoryId(fnCategory.getCategoryId())
                .status(Status.OK)
                .price(cRequest.getProduct().getPrice()).build();

        try {
            var sProductsDto = this.saveItem(productDto);
            return ResponseBase.of(RETURN_TP.OK, sProductsDto);
        } catch(Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    public ResponseBase<Object> resProductUpdate(CrudRequest cRequest, BrandService brandService, CategoryService categoryService) {
        if (cRequest.getProduct() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요..[product null]");

        var fnBrand = brandService.getBrandByName(cRequest.getProduct().getBrandName());
        var fnCategory = categoryService.getCategoriesByCategoryDesc(cRequest.getProduct().getCategoryName()).stream().findFirst().orElse(null);

        if (fnBrand == null)
            fnBrand = brandService.saveItem(
                        BrandDto.builder()
                                .brandName(cRequest.getProduct().getBrandName())
                                .companyId(1L)
                                .build());

        if (fnCategory == null)
            fnCategory = categoryService.saveItem(
                        CategoryDto.builder()
                                .categoryDesc(cRequest.getProduct().getCategoryName())
                                .categoryName(cRequest.getProduct().getCategoryName()).build());

        if (fnBrand == null || fnCategory == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(브랜드/카테고리 신규등록 실패).. [" + cRequest.getProduct() + "]");

        var productNm = StringUtils.isEmpty(cRequest.getProduct().getProductName())?
                            fnCategory.getCategoryDesc()
                            : cRequest.getProduct().getProductName();



        ProductsDto fProductDto = this.getProductsBYId(cRequest.getProduct().getProductId());

        Status status = null;
        try {
            status = cRequest.getProduct().getStatus();
        } catch(Exception e) {
            // doNothing
        }

        ProductsDto nProductDto = ProductsDto.builder()
                                    .productId(fProductDto.getProductId())
                                    .brandId(fnBrand.getBrandId())
                                    .categoryId(fnCategory.getCategoryId())
                                    .productName(productNm)
                                    .price(cRequest.getProduct().getPrice())
                                    .status(status)
                                    .build();

        // 업데이트 대상 컬럼을 옮긴다.
        copyNonNullProperties(nProductDto, fProductDto);

        try {
            var sProductsDto = this.saveItem(fProductDto);
            return ResponseBase.of(RETURN_TP.OK, sProductsDto);
        } catch(Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }

    public ResponseBase<Object> resProductDelete(CrudRequest cRequest) {
        if (cRequest.getProduct().getProductId() == null)
            return ResponseBase.of(RETURN_TP.FAIL, "JSON 전달 객체를 확인해 주세요(product key is null).. [" + cRequest.getBrand() + "]");

        // 상태 delete 를 요청 한다,
        try {
            productsDao.updateRowsByProductId(cRequest.getProduct().getProductId(), Status.DELETE);
            ProductsDto fProductDto = this.getProductsBYId(cRequest.getProduct().getProductId());

            return ResponseBase.of(RETURN_TP.OK, fProductDto);
        } catch(Exception e) {
            return ResponseBase.of(RETURN_TP.FAIL, e.getMessage());
        }
    }
}
