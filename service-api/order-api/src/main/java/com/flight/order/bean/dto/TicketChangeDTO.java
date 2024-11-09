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
@ApiModel(value = "TicketChangeDTO", description = "用户改签DTO对象")
public class TicketChangeDTO implements Serializable {
    @ApiModelProperty("用户ID")
    @Null(message = "前端无需传值")
    private Long userId;

    @ApiModelProperty("航班号")
    @NotNull(message = "航班号不能为空")
    private String flightNo;

    @ApiModelProperty("订单号")
    @NotNull(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty("起飞时间")
    @NotNull(message = "起飞时间不能为空")
    private String departureTime;

    @ApiModelProperty("到达时间")
    @NotNull(message = "到达时间不能为空")
    private String arriveTime;

    @ApiModelProperty("舱位类型，经济1，头等2")
    @NotNull(message = "舱位类型不能为空")
    private Integer shippingSpace;

    @ApiModelProperty("价格")
    @NotNull(message = "价格不能为空")
    private Integer price;
}
