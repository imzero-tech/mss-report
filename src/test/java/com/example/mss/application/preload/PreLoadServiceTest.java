package com.example.mss.application.preload;

import com.example.mss.application.brand.service.BrandService;
import com.example.mss.application.category.service.CategoryService;
import com.example.mss.application.company.service.CompanyService;
import com.example.mss.application.product.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * packageName  : com.example.mss.application.preload
 * fileName     : TestPreLoadService
 * auther       :
 * date         : 2025. 1. 11.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 11.                   최초생성
 */
@SpringBootTest
public class PreLoadServiceTest {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductsService productsService;

    @Test
    void contextLoads() {
        assertThat(companyService).isNotNull();
        assertThat(brandService).isNotNull();
        assertThat(categoryService).isNotNull();
        assertThat(productsService).isNotNull();
    }

}
