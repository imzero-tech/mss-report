package com.example.mss.application.category.dao;

import com.example.mss.application.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 10.
 * Git : https://git.nwz.kr
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
}
