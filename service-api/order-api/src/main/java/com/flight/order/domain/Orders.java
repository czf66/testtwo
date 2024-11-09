package com.flight.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Lwwwwaaa
 * @since 2022/10/18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "Orders",description = "订单对象")
@TableName(value = "orders")
public class Orders implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "航班号")
    private String flightNo;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单时间")
    private String orderTime;

    @ApiModelProperty(value = "起飞时间")
    private String departureTime;

    @ApiModelProperty(value = "到达时间")
    private String arriveTime;

    @ApiModelProperty(value = "起飞省份")
    private String departureCity;

    @ApiModelProperty(value = "到达省份")
    private String ArriveCity;

    @ApiModelProperty(value = "舱位类型，经济1，头等2")
    private Integer shippingSpace;

    @ApiModelProperty(value = "头等舱价格")
    private String seat;

    @ApiModelProperty(value = "价格")
    private Integer price;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;
}
