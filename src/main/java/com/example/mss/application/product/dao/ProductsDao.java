package com.example.mss.application.product.dao;

import com.example.mss.application.common.dto.Status;
import com.example.mss.application.product.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName  : com.example.mss.application.product.dto
 * fileName     : ProductsDao
 * auther       : imzero-tech
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      imzero-tech             최초생성
 */
@Repository
public interface ProductsDao extends JpaRepository<Products, Long>, ProductCustomDao {
    List<Products> findByBrandId(Products products);
    List<Products> findByStatusIn(List<Status> statuses);
    Products findByProductId(Long productsId);

    @Modifying
    @Transactional
    @Query("UPDATE PRODUCTS p SET p.status = :status WHERE p.brandId = :brandId")
    int updateRowsByBrandId(@Param("brandId") Long brandId, @Param("status") Status status);

    @Modifying
    @Transactional
    @Query("UPDATE PRODUCTS p SET p.status = :status WHERE p.productId = :productId")
    int updateRowsByProductId(@Param("productId") Long productId, @Param("status") Status status);
}
