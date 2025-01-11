package com.example.mss.application.product.dao;

import com.example.mss.application.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
