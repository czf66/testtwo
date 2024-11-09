package com.flight.user.controller;

import com.flight.base.util.JwtUtils;
import com.flight.base.util.R;
import com.flight.user.bean.dto.UpdateDTO;
import com.flight.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private UserService userService;

    @PostMapping("/updateInfo")
    @ApiOperation("修改用户信息")
    public R updateInfo(@Validated @RequestBody UpdateDTO updateDTO, @ApiIgnore HttpServletRequest request) {
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        Long userId = Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")));
        updateDTO.setUserId(userId);
        Integer result = userService.updateInfo(updateDTO);
        if (result == 1) {
            return R.ok();
        }
        if (result == 2) {
            return R.error("该邮箱已被别人使用！");
        }
        if (result == 3) {
            return R.error("该手机号已被别人使用！");
        }
        if (result == 4) {
            return R.error("请输入正确的旧密码");
        }
        return R.error();
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    public R getUserInfo(@ApiIgnore HttpServletRequest request) {
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        Long userId = Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")));
        return R.data(userService.getInfo(userId));
    }
}
