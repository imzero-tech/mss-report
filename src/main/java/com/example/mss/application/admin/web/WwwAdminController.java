package com.example.mss.application.admin.web;

import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.company.service.CompanyService;
import com.example.mss.application.product.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * packageName  : com.example.mss.application.admin.web
 * fileName     : WwwAdminController
 * auther       : imzero-tech
 * date         : 2025. 1. 12.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 12.      imzero-tech             최초생성
 */
@Slf4j
@RequiredArgsConstructor
@Controller("wwwAdimnController")
@RequestMapping("/admin")
public class WwwAdminController {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final CompanyService companyService;
    private final ProductsService productsService;

    @GetMapping(value ={"/", ""} )
    private ModelAndView home(ModelAndView mnv) {
        mnv.setViewName("admin/index");

        return mnv;
    }
}
