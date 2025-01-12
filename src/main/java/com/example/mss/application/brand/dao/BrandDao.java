package com.example.mss.application.brand.dao;

import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.common.dto.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName  : com.example.mss.application.brand.dao
 * fileName     : BrandDao
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {
    Brand findBrandByBrandId(Long brandId);
    Brand findBrandByBrandName(String brandName);
    List<Brand> findByStatus(Status status);
    Brand save(Brand brand);

}
