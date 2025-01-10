package com.example.mss.application.brand.dao;

import com.example.mss.application.brand.entitiy.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {
}
