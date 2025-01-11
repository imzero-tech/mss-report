package com.example.mss.application.product.dao.impl;

import com.example.mss.application.brand.entitiy.QBrand;
import com.example.mss.application.category.dto.CategoryDto;
import com.example.mss.application.product.dao.ProductCustomDao;
import com.example.mss.application.product.entity.Products;
import com.example.mss.application.product.entity.QProducts;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

import static com.example.mss.application.brand.entitiy.QBrand.brand;
import static com.example.mss.application.product.entity.QProducts.products;

/**
 * packageName  : com.example.mss.application.product.dao.impl
 * fileName     : ProductCustomDaoImpl
 * auther       : imzero-tech
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 11.     imzero-tech             add findAllMinPriceLeftFetchJoin
 * 2025. 1. 9.      imzero-tech             최초생성
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
    public List<Tuple> findAllMinPriceFetchJoin() {
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

    private List<Long> lowestPriceBrandId() {
        QProducts subProduct = new QProducts("subProduct");
        NumberExpression<Integer> sumPrice = subProduct.price.sum();
        return jpaQueryFactory
                .select(subProduct.brandId)
                .from(subProduct)
                .groupBy(subProduct.brandId)
                .orderBy(sumPrice.asc())
                .limit(1)
                .fetch();
    }

    @Override
    public List<Tuple> findBrandMinPriceFetchJoin() {
        return jpaQueryFactory
                .select(
                        products.price,
                        products.productName,
                        JPAExpressions
                                .select(brand.brandName)
                                .from(brand)
                                .where(brand.brandId.eq(products.brandId))
                )
                .from(products)
                .where(products.brandId.in(lowestPriceBrandId()))
                .orderBy(products.categoryId.asc())
                .fetch();
    }

    private List<Tuple> findBrandMinFetchJoin(CategoryDto categoryDto) {
        return jpaQueryFactory
                .select(
                        Expressions.asString("min"),
                        brand.brandName,
                        products.price
                )
                .from(products)
                .leftJoin(brand).on(products.brandId.eq(brand.brandId))
                .where(products.categoryId.eq(categoryDto.getCategoryId())
                        .and(products.price.eq(
                                JPAExpressions.select(products.price.min())
                                        .from(products)
                                        .where(products.categoryId.eq(categoryDto.getCategoryId()))
                                        .groupBy(products.categoryId))))
                .fetch();
    }

    private List<Tuple> findBrandMaxFetchJoin(CategoryDto categoryDto) {
        return jpaQueryFactory
                .select(
                        Expressions.asString("max"),
                        brand.brandName,
                        products.price
                )
                .from(products)
                .leftJoin(brand).on(products.brandId.eq(brand.brandId))
                .where(products.categoryId.eq(categoryDto.getCategoryId())
                        .and(products.price.eq(
                                JPAExpressions.select(products.price.max())
                                        .from(products)
                                        .where(products.categoryId.eq(categoryDto.getCategoryId()))
                                        .groupBy(products.categoryId))))
                .fetch();
    }

    @Override
    public List<Tuple> findBrandMinMaxFetchJoin(CategoryDto categoryDto) {
        List<Tuple> minResults = findBrandMinFetchJoin(categoryDto);
        List<Tuple> maxResults = findBrandMaxFetchJoin(categoryDto);

        return Stream.concat(maxResults.stream(), minResults.stream()).toList();
    }
}
