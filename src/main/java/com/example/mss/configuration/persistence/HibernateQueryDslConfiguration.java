package com.example.mss.configuration.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Slf4j
@Configuration
//@RequiredArgsConstructor
//@EnableJpaRepositories(
//        basePackageClasses = WebMvcConfiguration.class,
//        entityManagerFactoryRef = "entityManagerFactoryHibernate",
//        transactionManagerRef = "transactionManagerHibernate",
//        includeFilters = @ComponentScan.Filter(classes = {QueryDslHybernate.class})
//)
public class HibernateQueryDslConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
       return new JPAQueryFactory(entityManager);
    }
}
