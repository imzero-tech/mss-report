package com.example.mss.application.company.dao;

import com.example.mss.application.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Repository
public interface CompanyDao extends JpaRepository<Company, Long> {

}
