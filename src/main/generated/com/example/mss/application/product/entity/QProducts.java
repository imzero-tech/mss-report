package com.example.mss.application.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProducts is a Querydsl query type for Products
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProducts extends EntityPathBase<Products> {

    private static final long serialVersionUID = 1088274624L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProducts products = new QProducts("products");

    public final com.example.mss.application.brand.entitiy.QBrand brand;

    public final NumberPath<Integer> brandId = createNumber("brandId", Integer.class);

    public final com.example.mss.application.category.entity.QCategory category;

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final DateTimePath<java.time.Instant> crtDt = createDateTime("crtDt", java.time.Instant.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public final EnumPath<com.example.mss.application.common.dto.Status> stauts = createEnum("stauts", com.example.mss.application.common.dto.Status.class);

    public final DateTimePath<java.time.Instant> updDt = createDateTime("updDt", java.time.Instant.class);

    public QProducts(String variable) {
        this(Products.class, forVariable(variable), INITS);
    }

    public QProducts(Path<? extends Products> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProducts(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProducts(PathMetadata metadata, PathInits inits) {
        this(Products.class, metadata, inits);
    }

    public QProducts(Class<? extends Products> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new com.example.mss.application.brand.entitiy.QBrand(forProperty("brand"), inits.get("brand")) : null;
        this.category = inits.isInitialized("category") ? new com.example.mss.application.category.entity.QCategory(forProperty("category")) : null;
    }

}

