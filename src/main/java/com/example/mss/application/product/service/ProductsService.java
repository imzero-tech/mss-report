package com.example.mss.application.product.service;

import com.example.mss.application.product.dao.ProductsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Project : mss
 * Create by NWZ-zero on 2025. 1. 9.
 * Git : https://git.nwz.kr
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsDao producteRepository;
}
