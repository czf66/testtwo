package com.flight.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flight.user.bean.dto.LoginDTO;
import com.flight.user.bean.dto.RegisterDTO;
import com.flight.user.bean.dto.UpdateDTO;
import com.flight.user.bean.vo.InfoVO;
import com.flight.user.domain.User;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
public interface UserService extends IService<User> {
    InfoVO login(LoginDTO loginDTO);
    Integer register(RegisterDTO registerDTO);
    Integer updateInfo(UpdateDTO updateDTO);
    InfoVO getInfo(Long userId);
}
