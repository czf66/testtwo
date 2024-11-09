package com.flight.order.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author Lwwwwaaa
 * @since 2022/10/18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SeatChangeDTO", description = "更换座位DTO对象")
public class SeatChangeDTO implements Serializable {
    @ApiModelProperty("用户ID")
    @Null(message = "前端无需传值")
    private Long userId;

    @ApiModelProperty("订单号")
    @NotNull(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty("座位号")
    @NotNull(message = "座位号不能为空")
    private String seat;
}
