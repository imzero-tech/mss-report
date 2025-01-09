package com.example.mss.application.company.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 376419411L;

    public static final QCompany company = new QCompany("company");

    public final ListPath<com.example.mss.application.brand.entitiy.Brand, com.example.mss.application.brand.entitiy.QBrand> brands = this.<com.example.mss.application.brand.entitiy.Brand, com.example.mss.application.brand.entitiy.QBrand>createList("brands", com.example.mss.application.brand.entitiy.Brand.class, com.example.mss.application.brand.entitiy.QBrand.class, PathInits.DIRECT2);

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.time.Instant> crtDt = createDateTime("crtDt", java.time.Instant.class);

    public final EnumPath<com.example.mss.application.common.dto.Status> stauts = createEnum("stauts", com.example.mss.application.common.dto.Status.class);

    public final DateTimePath<java.time.Instant> updDt = createDateTime("updDt", java.time.Instant.class);

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}

