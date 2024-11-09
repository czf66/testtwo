package com.flight.user.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "InfoVO",description = "用户信息VO对象")
public class InfoVO implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "token")
    private String token;
}
