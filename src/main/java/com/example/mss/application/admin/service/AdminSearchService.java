package com.example.mss.application.admin.service;

import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.common.dto.AdminSearchKind;
import com.example.mss.application.company.service.CompanyService;
import com.example.mss.application.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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
        mnv.addObject("brands", brands);
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
        var products = productsService.getProductsAll();
        mnv.addObject("products", products);
        return mnv;
    }
}
