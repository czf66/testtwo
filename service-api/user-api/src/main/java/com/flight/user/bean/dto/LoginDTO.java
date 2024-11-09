package com.flight.user.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "LoginDTO", description = "用户登录DTO对象")
public class LoginDTO implements Serializable {
    @ApiModelProperty("账号")
    @NotNull(message = "账号不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty("登录类型，email：邮箱，phone_number：手机号")
    @NotNull(message = "登录类型不能为空")
    private String type;
}
