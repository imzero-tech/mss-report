package com.example.mss.application.common.dto;

import lombok.Getter;

/**
 * packageName  : com.example.mss.application.company.dao
 * fileName     : Constants
 * auther       : imzero-tech
 * date         : 2025. 1. 8.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 8.      imzero-tech             최초생성
 */
@Getter
public enum Constants {
    INIT_PROFILE_TEST("test");

    private String code;

    Constants(String code) {
        this.code = code;
    }
}
