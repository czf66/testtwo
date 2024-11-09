package com.flight.query.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flight.query.bean.vo.QueryByFlightNoVO;
import com.flight.query.bean.vo.QueryByProvinceVO;
import com.flight.query.bean.vo.QueryFlightVO;
import com.flight.query.domain.Flight;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
public interface QueryService extends IService<Flight> {
    JSONObject searchFlightByProvince(String province, Integer page, Integer pageSize, Integer type);
    JSONObject searchFlightByNumber(String flightNo, Integer page, Integer pageSize);
    JSONObject searchFlight(String province1, String province2, String date, Integer page, Integer pageSize, Integer shippingSpaceType, Integer screenType);
}
