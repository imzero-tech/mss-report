package com.example.mss.application.category.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -56103095L;

    public static final QCategory category = new QCategory("category");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final StringPath categoryName = createString("categoryName");

    public final DateTimePath<java.time.Instant> crtDt = createDateTime("crtDt", java.time.Instant.class);

    public final ListPath<com.example.mss.application.product.entity.Products, com.example.mss.application.product.entity.QProducts> Products = this.<com.example.mss.application.product.entity.Products, com.example.mss.application.product.entity.QProducts>createList("Products", com.example.mss.application.product.entity.Products.class, com.example.mss.application.product.entity.QProducts.class, PathInits.DIRECT2);

    public final EnumPath<com.example.mss.application.common.dto.Status> stauts = createEnum("stauts", com.example.mss.application.common.dto.Status.class);

    public final DateTimePath<java.time.Instant> updDt = createDateTime("updDt", java.time.Instant.class);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

