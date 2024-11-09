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
@ApiModel(value = "AddOrderDTO", description = "创建订单DTO对象")
public class AddOrderDTO implements Serializable {
    @ApiModelProperty("用户ID")
    @Null(message = "用户ID，前端无需传值")
    private Long userId;

    @ApiModelProperty("航班号")
    @NotNull(message = "航班号不能为空")
    private String flightNo;

    @ApiModelProperty("订单号")
    @Null(message = "订单号，前端无需传值")
    private String orderNo;

    @ApiModelProperty("创建订单时间")
    @Null(message = "订单时间，前端无需传值")
    private String orderTime;

    @ApiModelProperty("起飞时间")
    @NotNull(message = "起飞时间不能为空")
    private String departureTime;

    @ApiModelProperty("到达时间")
    @NotNull(message = "到达时间不能为空")
    private String arriveTime;

    @ApiModelProperty("起飞省份")
    @NotNull(message = "起飞省份不能为空")
    private String departureCity;

    @ApiModelProperty("到达省份")
    @NotNull(message = "到达省份不能为空")
    private String arriveCity;

    @ApiModelProperty("舱位类型，经济1，头等2")
    @NotNull(message = "舱位类型不能为空")
    private Integer shippingSpace;

    @ApiModelProperty("座位")
    @Null(message = "座位，前端无需传值")
    private String seat;

    @ApiModelProperty("价格")
    @NotNull(message = "价格不能为空")
    private Integer price;
}
