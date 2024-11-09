package com.flight.user.controller;

import com.flight.base.util.JwtUtils;
import com.flight.base.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 在token过期前打开小程序将会延长token，避免使用时token失效
 * @author Lwwwwaaa
 * @since 2022/10/18
 */
@RestController
public class extendToken {
    @GetMapping("/extendToken")
    public R extendToken(@ApiIgnore HttpServletRequest request) {
        if (!JwtUtils.checkToken(request)) {// 过期了，让用户登录
            return R.error();
        }
        // 没过期，延长时间
        return R.data(JwtUtils.getJwtToken(Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")))));
    }
}
