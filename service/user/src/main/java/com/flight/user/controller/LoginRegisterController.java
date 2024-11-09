package com.flight.user.controller;

import cn.hutool.http.HttpRequest;
import com.flight.base.util.JwtUtils;
import com.flight.base.util.R;
import com.flight.base.util.SnowflakeIdWorker;
import com.flight.user.bean.dto.LoginDTO;
import com.flight.user.bean.dto.RegisterDTO;
import com.flight.user.bean.vo.InfoVO;
import com.flight.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/flygo/loginRegister")
@CrossOrigin
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ApiOperation("测试")
    public R test(@ApiIgnore HttpServletRequest request) {
        if (JwtUtils.checkToken(request)) {
            return R.data(JwtUtils.getMemberByJwtToken(request));
        }
        return R.error();
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
//    @CrossOrigin(origins = "*")
    public R login(@Validated @RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        System.out.println(loginDTO);
        InfoVO infoVO = userService.login(loginDTO);
        request.getSession().setAttribute("token",infoVO.getToken());
        if (infoVO != null) {
            return R.data(infoVO);
        }
        return R.error("登录失败");
    }

    @GetMapping("/logout")
    @ApiOperation("用户登出")
    public R logout(@ApiIgnore HttpSession session){
        session.invalidate();
        return R.ok();
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public R register(@Validated @RequestBody RegisterDTO registerDTO) {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        registerDTO.setUserId(snowflakeIdWorker.nextId());
        Integer result = userService.register(registerDTO);
        if (result == 1) {
            return R.ok();
        }
        if (result == 2) {
            return R.error("此手机号已被注册！");
        }
        if (result == 3) {
            return R.error("此邮箱已被注册！");
        }
        if (result == 4) {
            return R.error("此账号已被注册！");
        }
        return R.error();
    }
}
