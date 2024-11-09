package com.flight.query.bean.vo;

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
@ApiModel(value = "QueryFlightVO", description = "搜索VO对象")
public class QueryFlightVO implements Serializable {
    @ApiModelProperty(value = "飞机类型")
    private String flightType;

    @ApiModelProperty(value = "航班号")
    private String flightNo;

    @ApiModelProperty(value = "起飞机场")
    private String departureAirport;

    @ApiModelProperty(value = "到达机场")
    private String arriveAirport;

    @ApiModelProperty(value = "起飞时间")
    private String departureTime;

    @ApiModelProperty(value = "到达时间")
    private String arriveTime;

    @ApiModelProperty(value = "头等舱座位价格")
    private Integer seatFPrice;

    @ApiModelProperty(value = "经济舱座位价格")
    private Integer seatYPrice;

    @ApiModelProperty(value = "航班飞行时长")
    private String flightDuration;
}
