package com.example.mss.application.category.service;

import com.example.mss.application.brand.dto.BrandDto;
import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.category.dao.CategoryDao;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.entity.Category;
import com.example.mss.application.common.dto.Constants;
import com.example.mss.application.common.dto.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName  : com.example.mss.application.category.service
 * fileName     : CategoryService
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
public class CategoryService {
    private final CategoryDao categoryDao;
    private final ModelMapper modelMapper;

    @Value("${spring.config.activate.on-profile:default}")
    private String onProfile;

    public List<CategoryDto> saveInitCategories(List<CategoryDto> categoryDtos) {
        if (!Constants.INIT_PROFILE_TEST.getCode().equals(onProfile))
            return null;

        // saveAll : 하나의 connection 으로 다수의 dml 질의
        // ㄴ. insert bulk가 효과적
        return categoryDao
                .saveAll(categoryDtos.stream()
                        .map(o -> modelMapper.map(o, Category.class))
                        .toList())
                .stream()
                .map(dest -> modelMapper.map(dest, CategoryDto.class))
                .toList();
    }

    public CategoryDto saveItem(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category fcategory = categoryDao.saveAndFlush(category);
        return modelMapper.map(fcategory, CategoryDto.class);
    }

    public List<CategoryDto> getCategoriesByStatus(Status status) {
        return categoryDao.findCategoriesByStatus(status)
                .stream()
                .map(dest -> modelMapper.map(dest, CategoryDto.class))
                .toList();
    }

    public List<CategoryDto> getCategoriesByCategoryDesc(String categoryDesc) {
        return categoryDao.findCategoriesByCategoryDesc(categoryDesc)
                .stream()
                .map(dest -> modelMapper.map(dest, CategoryDto.class))
                .toList();
    }
}
