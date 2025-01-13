package com.example.mss.application.admin.web;

import com.example.mss.application.admin.service.AdminSearchService;
import com.example.mss.application.common.dto.AdminSearchKind;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.example.mss.application.util.MssClassUtil.isValidEnum;

/**
 * packageName  : com.example.mss.application.admin.web
 * fileName     : WwwAdminController
 * auther       : imzero-tech
 * date         : 2025. 1. 12.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 12.      imzero-tech             최초생성
 */
@Slf4j
@RequiredArgsConstructor
@Controller("wwwAdimnController")
@RequestMapping("/admin")
public class WwwAdminController {

    private final AdminSearchService adminSearchService;

    @GetMapping(value ={"/", ""} )
    private ModelAndView home(ModelAndView mnv) {
        mnv.setViewName("admin/index");

        return mnv;
    }

    @GetMapping(value ="/search/{kind}" )
    private ModelAndView searchView(ModelAndView mnv, @PathVariable String kind) {
        // 잘못된 접근 처리
        if (StringUtils.isEmpty(kind) || !isValidEnum(AdminSearchKind.class, kind)) {
            mnv.setViewName("admin/index");
            return mnv;
        }

        return adminSearchService.searchListAll(mnv, AdminSearchKind.valueOf(kind));
    }
}
