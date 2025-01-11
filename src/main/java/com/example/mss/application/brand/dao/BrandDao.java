package com.example.mss.application.brand.dao;

import com.example.mss.application.brand.entitiy.Brand;
import com.example.mss.application.common.dto.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {
    List<Brand> findByStatus(Status status);
}
