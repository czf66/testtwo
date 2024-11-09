package com.flight.user.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UpdateDTO", description = "用户修改信息DTO对象")
public class UpdateDTO {
    @ApiModelProperty("用户ID")
    @Null(message = "前端无需提供")
    private Long userId;

    @ApiModelProperty("姓名")
    @Size(max = 10, message = "姓名长度不能大于20位")
    private String newName;

    @ApiModelProperty("邮箱")
    @Size(max = 20, message = "邮箱长度不能大于20位")
    private String newEmail;

    @ApiModelProperty("手机号")
    @Size(max = 20, message = "手机号长度不能大于20位")
    private String newPhoneNumber;

    @ApiModelProperty("旧密码")
    @Size(max = 20, message = "旧密码长度不能大于20位")
    private String oldPassword;

    @ApiModelProperty("密码")
    @Size(max = 20, message = "密码长度不能大于20位")
    private String newPassword;
}
