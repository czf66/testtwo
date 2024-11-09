package com.flight.user.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "RegisterDTO", description = "用户注册DTO对象")
public class RegisterDTO implements Serializable {
    @ApiModelProperty("用户ID")
    @Null(message = "前端无需提供")
    private Long userId;

    @ApiModelProperty("姓名")
    @NotNull(message = "姓名不能为空")
    @Size(max = 10, message = "姓名长度不能大于10位")
    private String name;

    @ApiModelProperty("邮箱")
    @NotNull(message = "邮箱不能为空")
    @Size(max = 20, message = "邮箱长度不能大于20位")
    private String email;

    @ApiModelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    @Size(max = 20, message = "手机号长度不能大于20位")
    private String phoneNumber;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    @Size(max = 20, message = "密码长度不能大于20位")
    private String password;
}
