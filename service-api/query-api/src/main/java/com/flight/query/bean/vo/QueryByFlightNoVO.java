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
@ApiModel(value = "QueryByFlightNoVO", description = "通过航班号查询VO对象")
public class QueryByFlightNoVO implements Serializable {
    @ApiModelProperty(value = "航班号")
    private String flightNo;

    @ApiModelProperty(value = "起飞机场")
    private String arriveAirport;

    @ApiModelProperty(value = "到达时间")
    private String departureTime;
}
