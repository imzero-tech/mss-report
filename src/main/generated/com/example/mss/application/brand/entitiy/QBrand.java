package com.example.mss.application.brand.entitiy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBrand is a Querydsl query type for Brand
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBrand extends EntityPathBase<Brand> {

    private static final long serialVersionUID = -1290340120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBrand brand = new QBrand("brand");

    public final NumberPath<Long> brandId = createNumber("brandId", Long.class);

    public final StringPath brandName = createString("brandName");

    public final com.example.mss.application.company.entity.QCompany company;

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final DateTimePath<java.time.Instant> crtDt = createDateTime("crtDt", java.time.Instant.class);

    public final ListPath<com.example.mss.application.product.entity.Products, com.example.mss.application.product.entity.QProducts> products = this.<com.example.mss.application.product.entity.Products, com.example.mss.application.product.entity.QProducts>createList("products", com.example.mss.application.product.entity.Products.class, com.example.mss.application.product.entity.QProducts.class, PathInits.DIRECT2);

    public final EnumPath<com.example.mss.application.common.dto.Status> stauts = createEnum("stauts", com.example.mss.application.common.dto.Status.class);

    public final DateTimePath<java.time.Instant> updDt = createDateTime("updDt", java.time.Instant.class);

    public QBrand(String variable) {
        this(Brand.class, forVariable(variable), INITS);
    }

    public QBrand(Path<? extends Brand> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBrand(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBrand(PathMetadata metadata, PathInits inits) {
        this(Brand.class, metadata, inits);
    }

    public QBrand(Class<? extends Brand> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.example.mss.application.company.entity.QCompany(forProperty("company")) : null;
    }

}

