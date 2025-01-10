package com.example.mss.application.preload.service;

import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.common.dto.Status;
import com.example.mss.application.company.dto.CompanyDto;
import com.example.mss.application.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * packageName  : com.example.mss.application.preload.service
 * fileName     : PreLoadService
 * auther       : zero
 * date         : 2025. 1. 6.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 6.      zero             최초생성
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PreLoadService {

    private final CategoryService categoryService;
    @Value("${spring.config.activate.on-profile:default}")
    private String onProfile;

    private final CompanyService companyService;
    private final BrandService brandService;

    public void preLoad() {
        log.info("******************************************************");
        log.info("**********           Warmup Start          ***********");
        log.info("******************************************************");
        log.info("********** onProfile : {}/ boolean: {} **********", onProfile, Constants.INIT_PROFILE_TEST.getCode().equals(onProfile));

        if (Constants.INIT_PROFILE_TEST.getCode().equals(onProfile)) {
            // 회사 데이타 등록
            initCompany();

            // 브랜드 데이타 등록
            initBrandes();

            // 카테고리 데이타 등록
            initCategories();
        }
    }

    private void initCompany() {
        log.info("**********          Company Start          ***********");
        companyService.saveInitCompanies(
                List.of(CompanyDto.builder()
                            .companyId(1L)
                            .companyName("Musinsa")
                            .stauts(Status.OK)
                            .build()));
    }

    private List<Character> getAlphabetWithStream() {
        return IntStream.rangeClosed('A', 'J')
                .mapToObj(c -> (char) c)
                .toList();
    }

    private void initBrandes() {
        log.info("**********          Brand Start          ***********");
        List<BrandDto> brandDtos = getAlphabetWithStream().stream()
                            .map(brandNm ->
                                BrandDto.builder()
                                        .brandName(String.valueOf(brandNm))
                                        .companyId(1L)
                                        .stauts(Status.OK)
                                        .build())
                            .toList();
        log.info("[brandDtos] : [{}]", brandDtos);
        brandService.saveInitBrands(brandDtos);
    }

    private void initCategories() {
        var categories = Map.of(
                1L, Map.of("tops", "상의")
                , 2L, Map.of("outer", "아우터")
                , 3L, Map.of("pants", "바지")
                , 4L, Map.of("sneakers", "스니커즈")
                , 5L, Map.of("bags", "가방")
                , 6L, Map.of("hats", "모자")
                , 7L, Map.of("socks", "양말")
                , 8L, Map.of("accessories", "액세서리")
        );
        List<CategoryDto> categoryDtos = categories.entrySet().stream()
                .flatMap(entry -> {
                    Long id = entry.getKey();  // 키 (숫자 ID)
                    Map<String, String> innerMap = entry.getValue();  // 내부 맵

                    return innerMap.entrySet().stream()
                            .map(innerEntry -> CategoryDto.builder()
                                    .categoryId(id)
                                    .categoryName(innerEntry.getKey())
                                    .categoryDesc(innerEntry.getValue())
                                    .stauts(Status.OK)
                                    .build());
                })
                .toList();

        categoryService.saveInitCategories(categoryDtos);
    }
}
