package com.flight.order.bean.vo;

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
@ApiModel(value = "OrderInfoVO",description = "订单信息VO对象")
public class OrderInfoVO implements Serializable {
    @ApiModelProperty("航班号")
    private String flightNo;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("创建订单时间")
    private String orderTime;

    @ApiModelProperty("起飞时间")
    private String departureTime;

    @ApiModelProperty("到达时间")
    private String arriveTime;

    @ApiModelProperty("起飞省份")
    private String departureCity;

    @ApiModelProperty("到达省份")
    private String arriveCity;

    @ApiModelProperty("舱位类型，经济1，头等2")
    private Integer shippingSpace;

    @ApiModelProperty("座位")
    private String seat;

    @ApiModelProperty("价格")
    @NotNull(message = "价格不能为空")
    private Integer price;
}
