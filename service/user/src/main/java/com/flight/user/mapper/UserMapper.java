package com.flight.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flight.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    Boolean getOneByEmail(String email);
    Boolean getOneByPhoneNumber(String email);
    Boolean getOneByPassword(@Param(value = "userId") String userId, @Param(value = "oldPassword") String oldPassword);
}
