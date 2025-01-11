package com.example.mss.application.company.dao;

import com.example.mss.application.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName  : com.example.mss.application.company.dao
 * fileName     : CompanyDao
 * auther       : imzero-tech
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      imzero-tech             최초생성
 */
@Repository
public interface CompanyDao extends JpaRepository<Company, Long> {

}
