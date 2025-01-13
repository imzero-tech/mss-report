package com.example.mss.application.admin.service;

import com.example.mss.application.admin.dto.AdminBrandDto;
import com.example.mss.application.admin.dto.AdminProductDto;
import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.common.dto.AdminSearchKind;
import com.example.mss.application.company.dto.CompanyDto;
import com.example.mss.application.company.service.CompanyService;
import com.example.mss.application.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName  : com.example.mss.application.admin.service
 * fileName     : CompanyDto
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
@Service
public class AdminSearchService {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final CompanyService companyService;
    private final ProductsService productsService;

    private static final String UNKNOWN = "알수없음";
    private final ModelMapper modelMapper;

    public ModelAndView searchListAll(ModelAndView mnv, AdminSearchKind adminSearchKind) {

        mnv.setViewName("admin/search/" + adminSearchKind);
        return switch(adminSearchKind) {
            case brand -> brandListAll(mnv);
            case category -> categoryListAll(mnv);
            case company -> companyListAll(mnv);
            case product -> productListAll(mnv);
        };
    }

    private ModelAndView brandListAll(ModelAndView mnv) {
        var brands = brandService.getBrandAll();
        var compines = companyService.getCategoryAll();

        List<AdminBrandDto> adminBrandDtoList = new ArrayList<>();
        // 회사명,
        brands.forEach(brand -> {
            var companyName = compines.stream().filter(c -> c.getCompanyId().equals(brand.getCompanyId())).map(CompanyDto::getCompanyName).findFirst().orElse(UNKNOWN);

            AdminBrandDto adminBrandDto = modelMapper.map(brand, AdminBrandDto.class);
            adminBrandDto.setCompanyName(companyName);
            adminBrandDtoList.add(adminBrandDto);
        });
        mnv.addObject("adminBrands", adminBrandDtoList);
        return mnv;
    }

    private ModelAndView categoryListAll(ModelAndView mnv) {
        var categories = categoryService.getCategoryAll();
        mnv.addObject("categories", categories);
        return mnv;
    }

    private ModelAndView companyListAll(ModelAndView mnv) {
        var compines = companyService.getCategoryAll();
        mnv.addObject("compines", compines);
        return mnv;
    }

    private ModelAndView productListAll(ModelAndView mnv) {
        var brands = brandService.getBrandAll();
        var categories = categoryService.getCategoryAll();
        var compines = companyService.getCategoryAll();
        var products = productsService.getProductsAll();

        List<AdminProductDto> adminProductDtoList = new ArrayList<>();
        // 브랜드명, 카테고리명, 회사명,
        products.forEach(product -> {
            var brandDto = brands.stream().filter(b -> b.getBrandId().equals(product.getBrandId())).findFirst().orElse(null);

            var brandName = UNKNOWN;
            var companyName = UNKNOWN;

            if (brandDto != null) {
                brandName = brandDto.getBrandName();
                companyName = compines.stream().filter(c -> c.getCompanyId().equals(brandDto.getCompanyId())).map(CompanyDto::getCompanyName).findFirst().orElse(UNKNOWN);
            } else {
                log.info(">>>> {}", product);
            }

            var categoryDesc = categories.stream().filter( c -> c.getCategoryId().equals(product.getCategoryId())).map(CategoryDto::getCategoryDesc).findFirst().orElse(UNKNOWN);

            AdminProductDto adminProductDto = modelMapper.map(product, AdminProductDto.class);
            adminProductDto.setBrandName(brandName);
            adminProductDto.setCategoryDesc(categoryDesc);
            adminProductDto.setCompanyName(companyName);

            adminProductDtoList.add(adminProductDto);
        });

        mnv.addObject("adminProducts", adminProductDtoList);
        return mnv;
    }
}
