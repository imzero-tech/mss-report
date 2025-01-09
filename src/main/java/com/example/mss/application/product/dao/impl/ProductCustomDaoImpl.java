package com.example.mss.application.product.dao.impl;

import com.example.mss.application.product.dao.ProductCustomDao;
import com.example.mss.application.product.entity.Products;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.mss.application.product.entity.QProducts.products;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Repository
@RequiredArgsConstructor
public class ProductCustomDaoImpl implements ProductCustomDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Products findAllLeftFetchJoein(Long productId) {
        return jpaQueryFactory.selectFrom(products)
                .where(products.productId.eq(productId))
                .fetchOne();
    }
}
