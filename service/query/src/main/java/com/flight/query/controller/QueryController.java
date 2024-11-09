package com.flight.query.controller;

import com.flight.base.util.R;
import com.flight.query.service.QueryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flygo")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @GetMapping("/searchFlightByProvince/{province}/{page}/{pageSize}/{type}")
    @ApiOperation("通过省份查询")
    public R searchFlightByProvince(@PathVariable String province,
                                    @PathVariable Integer page,
                                    @PathVariable Integer pageSize,
                                    @PathVariable Integer type) {
        return R.data(queryService.searchFlightByProvince(province, page, pageSize, type));
    }

    @GetMapping("/searchFlightByNumber/{flightNo}/{page}/{pageSize}")
    @ApiOperation("通过航班号查询")
    public R searchFlightByNumber(@PathVariable String flightNo,
                                  @PathVariable Integer page,
                                  @PathVariable Integer pageSize) {
        return R.data(queryService.searchFlightByNumber(flightNo, page, pageSize));
    }

    @GetMapping("/searchFlight/{province1}/{province2}/{date}/{page}/{pageSize}/{shippingSpaceType}/{screenType}")
    @ApiOperation("搜索航班")
    @CrossOrigin(origins = "*")
    public R searchFlight(@PathVariable String province1,
                                     @PathVariable String province2,
                                     @PathVariable String date,
                                     @PathVariable Integer page,
                                     @PathVariable Integer pageSize,
                                     @PathVariable Integer shippingSpaceType,
                                     @PathVariable Integer screenType) {

        return R.data(queryService.searchFlight(province1, province2, date, page, pageSize, shippingSpaceType, screenType));
    }
}
