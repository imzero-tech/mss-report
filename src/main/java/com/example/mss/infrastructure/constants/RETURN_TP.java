package com.example.mss.infrastructure.constants;

import java.util.Arrays;
import java.util.Optional;

/**
 * packageName  : com.example.mss.infrastructure.constants
 * fileName     : RETURN_TP
 * auther       : imzero-tech
 * date         : 2025. 1. 11.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 11.      imzero-tech             최초생성
 */
public enum RETURN_TP {
    OK("성공"),
    FAIL("실패");

    private String message;

    private RETURN_TP(String message) {
        this.message = message;
    }

    public Enum get() {
        return this;
    }

    public static Optional<RETURN_TP> find(String name) {
        return Arrays.stream(values()).filter((s) -> {
            return s.name().equalsIgnoreCase(name);
        }).findAny();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
