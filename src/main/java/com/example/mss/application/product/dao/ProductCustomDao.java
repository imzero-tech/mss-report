package com.example.mss.application.product.dao;

import com.example.mss.application.product.entity.Products;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
public interface ProductCustomDao {
    Products findAllLeftFetchJoein(Long productId);
}
