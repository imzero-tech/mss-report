package com.example.mss.application.category.dao;

import com.example.mss.application.category.entity.Category;
import com.example.mss.application.common.dto.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName  : com.example.mss.application.category.dao
 * fileName     : CategoryDao
 * auther       : zero
 * date         : 2025. 1. 10.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 10.      zero             최초생성
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByStatus(Status status);
}
