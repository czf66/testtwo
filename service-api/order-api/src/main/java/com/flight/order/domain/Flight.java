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
 * @since 2022/10/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "Flight",description = "用户对象")
@TableName(value = "flight")
public class Flight implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "承运公司")
    private String carrier;

    @ApiModelProperty(value = "飞机类型")
    private String flightType;

    @ApiModelProperty(value = "航班号")
    private String flightNo;

    @ApiModelProperty(value = "起飞省份")
    private String departureCity;

    @ApiModelProperty(value = "起飞机场")
    private String departureAirport;

    @ApiModelProperty(value = "到达省份")
    private String arriveCity;

    @ApiModelProperty(value = "到达机场")
    private String arriveAirport;

    @ApiModelProperty(value = "起飞时间")
    private String departureTime;

    @ApiModelProperty(value = "到达时间")
    private String arriveTime;

    @ApiModelProperty(value = "头等舱价格")
    private Integer seatFPrice;

    @ApiModelProperty(value = "经济舱价格")
    private Integer seatYPrice;

    @ApiModelProperty(value = "头等舱座位余量")
    private Integer seatFVacant;

    @ApiModelProperty(value = "经济舱座位余量")
    private Integer seatYVacant;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;
}
