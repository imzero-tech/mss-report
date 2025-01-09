package com.example.mss.application.product.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName  : com.example.mss.application.product.web
 * fileName     : ProductRestController
 * auther       : zero
 * date         : 2025. 1. 9.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 9.      zero             최초생성
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    @GetMapping(value = "/xpi/v1/product/srch/min/showbrand")
    public void t() {

    }
}
