package com.example.mss.application.category.service;

import com.example.mss.application.category.dao.CategoryDao;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.category.entity.Category;
import com.example.mss.application.common.dto.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 10.
 * Git : https://git.nwz.kr
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
                .saveAll(
                        categoryDtos.stream()
                                .map(o -> modelMapper.map(o, Category.class))
                                .toList())
                .stream()
                .map(dest -> modelMapper.map(dest, CategoryDto.class))
                .toList();
    }
}
