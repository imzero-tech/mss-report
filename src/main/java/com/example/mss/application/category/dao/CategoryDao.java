package com.example.mss.application.category.dao;

import com.example.mss.application.category.entity.Category;
import com.example.mss.application.common.dto.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 10.
 * Git : https://git.nwz.kr
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
    public List<Category> findCategoriesByStatus(Status status);
}
