package com.flight.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flight.base.util.JwtUtils;
import com.flight.user.bean.dto.LoginDTO;
import com.flight.user.bean.dto.RegisterDTO;
import com.flight.user.bean.dto.UpdateDTO;
import com.flight.user.bean.vo.InfoVO;
import com.flight.user.domain.User;
import com.flight.user.mapper.UserMapper;
import com.flight.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param loginDTO 登录dto
     * @return {@link Boolean}
     */
    @Override
    public InfoVO login(LoginDTO loginDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (loginDTO.getType().equals("email")) {
            queryWrapper.eq("account", loginDTO.getAccount());
        }
        if (loginDTO.getType().equals("phone_number")) {
            queryWrapper.eq("account", loginDTO.getAccount());
        }
        queryWrapper.eq("password", loginDTO.getPassword());
        queryWrapper.select("user_id", "name");
        System.out.println("##################"+loginDTO.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        InfoVO infoVO = new InfoVO();
        infoVO.setUserId(String.valueOf(user.getUserId()))
                .setName(user.getName())
                .setToken(JwtUtils.getJwtToken(user.getUserId()));
        return infoVO;
    }

    /**
     * 注册
     *
     * @param registerDTO 注册dto
     * @return {@link Integer}
     */
    @Override
    public Integer register(RegisterDTO registerDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", registerDTO.getEmail());
        if (userMapper.selectByMap(map).size() == 0) {
            map.clear();
            map.put("phone_number", registerDTO.getPhoneNumber());
            if (userMapper.selectByMap(map).size() == 0) {
                User user = new User()
                        .setUserId(registerDTO.getUserId())
                        .setName(registerDTO.getName())
                        .setEmail(registerDTO.getEmail())
                        .setPhoneNumber(registerDTO.getPhoneNumber())
                        .setPassword(registerDTO.getPassword());
                int result = userMapper.insert(user);
                if (result == 1) {
                    return 1;
                }
            } else {
                return 2;
            }
        } else {
            return 3;
        }
        return 4;
    }

    /**
     * 更新信息
     *
     * @param updateDTO 更新dto
     * @return {@link Integer}
     */
    @Override
    public Integer updateInfo(UpdateDTO updateDTO) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("user_id", updateDTO.getUserId());

        if (updateDTO.getNewName() != null && updateDTO.getNewName() != "") {
            updateWrapper.set("name", updateDTO.getNewName());
        }
        if (updateDTO.getNewEmail() != null && updateDTO.getNewEmail() != "") {
            if (userMapper.getOneByEmail(updateDTO.getNewEmail()) != null) {
                return 2;
            }
            updateWrapper.set("email", updateDTO.getNewEmail());
        }
        if (updateDTO.getNewPhoneNumber() != null && updateDTO.getNewPhoneNumber() != "") {
            if (userMapper.getOneByPhoneNumber(updateDTO.getNewPhoneNumber()) != null) {
                return 3;
            }
            updateWrapper.set("phone_number", updateDTO.getNewPhoneNumber());
        }
        if (updateDTO.getNewPassword() != null && updateDTO.getNewPassword() != "") {
            if (userMapper.getOneByPassword(String.valueOf(updateDTO.getUserId()), updateDTO.getOldPassword()) == null) {
                return 4;
            }
            updateWrapper.set("password", updateDTO.getNewPassword());
        }
        return userMapper.update(null, updateWrapper);
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return {@link InfoVO}
     */
    @Override
    public InfoVO getInfo(Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("name");
        User user = userMapper.selectOne(queryWrapper);

        InfoVO infoVO = new InfoVO();
        infoVO.setUserId(String.valueOf(userId))
                .setName(user.getName());
        return infoVO;
    }
}
