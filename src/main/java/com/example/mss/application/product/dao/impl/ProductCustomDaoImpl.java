package com.example.mss.application.product.dao.impl;

import com.example.mss.application.brand.entitiy.QBrand;
import com.example.mss.application.product.dao.ProductCustomDao;
import com.example.mss.application.product.entity.Products;
import com.example.mss.application.product.entity.QProducts;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Products findAllLeftFetchJoin(Long productId) {
        return jpaQueryFactory.selectFrom(products)
                .where(products.productId.eq(productId))
                .fetchOne();
    }

    @Override
    public List<Tuple> findAllMinPriceLeftFetchJoin() {
        QProducts product = QProducts.products; // 기존에 생성된 Q 타입 사용
        QProducts subProduct = new QProducts("subProduct");
        QBrand brand = QBrand.brand; // QBrand 타입도 정의되었다고 가정

        return jpaQueryFactory
                .select(product.categoryId, product.productName, brand.brandName, product.price)
                .from(product)
                .leftJoin(brand).on(product.brandId.eq(brand.brandId))
                .where(product.price.eq(
                        JPAExpressions
                                .select(subProduct.price.min())
                                .from(subProduct)
                                .where(subProduct.categoryId.eq(product.categoryId))
                                .groupBy(subProduct.categoryId)
                ))
                .orderBy(product.categoryId.asc())
                .fetch();
    }


}
