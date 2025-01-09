package com.example.mss.application.product.dao;

import com.example.mss.application.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Repository
public interface ProductsDao extends JpaRepository<Products, Long>, ProductCustomDao {
    List<Products> findByBrandId(Products products);
}
