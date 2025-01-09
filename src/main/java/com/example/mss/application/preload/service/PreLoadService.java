package com.example.mss.application.preload.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * packageName  : com.example.mss.application.preload.service
 * fileName     : PreLoadService
 * auther       : zero
 * date         : 2025. 1. 6.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 6.      zero             최초생성
 */
@Slf4j
@Service
public class PreLoadService {

    @Value("${spring.config.activate.on-profile:#{null}}}")
    private static String onProfile;
    private static final String initProfile = "test";

    public void preLoad() {
        log.warn("******************************************************");
        log.warn("**********           Warmup Start          ***********");
        log.warn("******************************************************");

        if (initProfile.equals(onProfile)) {
            // 기초 데이타 등록
        }
    }
}
