package com.example.mss.application.product.dao;

import com.example.mss.application.product.entity.Products;
import com.querydsl.core.Tuple;

import java.util.List;

/**
 * packageName  : com.example.mss.application.product.dao.impl
 * fileName     : ProductCustomDao
 * auther       : imzero-tech
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      imzero-tech             최초생성
 */
public interface ProductCustomDao {
    Products findAllLeftFetchJoin(Long productId);

    List<Tuple> findAllMinPriceLeftFetchJoin();
}
