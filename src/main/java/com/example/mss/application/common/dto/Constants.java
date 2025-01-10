package com.example.mss.application.common.dto;

import lombok.Getter;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 10.
 * Git : https://git.nwz.kr
 */
@Getter
public enum Constants {
    INIT_PROFILE_TEST("test");

    private String code;

    Constants(String code) {
        this.code = code;
    }
}
