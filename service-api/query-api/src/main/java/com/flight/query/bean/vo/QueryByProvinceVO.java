package com.flight.query.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "QueryByProvinceVO", description = "通过省份查询VO对象")
public class QueryByProvinceVO implements Serializable {
    @ApiModelProperty(value = "航班号")
    private String flightNo;

    @ApiModelProperty(value = "起飞机场")
    private String arriveAirport;

    @ApiModelProperty(value = "到达时间")
    private String departureTime;
}
