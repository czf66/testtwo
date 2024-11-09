package com.flight.query.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flight.query.bean.vo.QueryByFlightNoVO;
import com.flight.query.bean.vo.QueryByProvinceVO;
import com.flight.query.bean.vo.QueryFlightVO;
import com.flight.query.domain.Flight;
import com.flight.query.mapper.FlightMapper;
import com.flight.query.service.QueryService;
import com.flight.query.utils.DatetimeSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Service
public class QueryServiceImpl extends ServiceImpl<FlightMapper, Flight> implements QueryService {

    @Resource
    private FlightMapper flightMapper;

    @Autowired
    private DatetimeSub datetimeSub;

    /**
     * 通过省份查询航班
     *
     * @param province 省份
     * @param page     页面
     * @param pageSize 页面大小
     * @param type     类型
     * @return {@link List}<{@link QueryByProvinceVO}>
     */
    @Override
    public JSONObject searchFlightByProvince(String province, Integer page, Integer pageSize, Integer type) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("flight_no", "arrive_airport", "departure_time");
        // 当前的城市是起点
        if (type == 1) {
            wrapper.eq("departure_city", province);
        }
        // 当前的城市是终点
        if (type == 2) {
            wrapper.eq("arrive_city", province);
        }
        IPage<Flight> list = flightMapper.selectPage(new Page<>(page, pageSize), wrapper);
        List<QueryByProvinceVO> queryByProvinceVOList = new ArrayList<>();
        for (int i = 0; i < list.getRecords().size(); i++) {
            QueryByProvinceVO queryByProvinceVO = new QueryByProvinceVO();
            queryByProvinceVO.setFlightNo(list.getRecords().get(i).getFlightNo())
                    .setArriveAirport(list.getRecords().get(i).getArriveAirport())
                    .setDepartureTime(list.getRecords().get(i).getDepartureTime());

            queryByProvinceVOList.add(i, queryByProvinceVO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("list", queryByProvinceVOList);
        jsonObject.append("total", list.getTotal());
        return jsonObject;
    }

    /**
     * 通过航班号查询航班
     *
     * @param flightNo 航班号
     * @param page     页面
     * @param pageSize 页面大小
     * @return {@link List}<{@link QueryByFlightNoVO}>
     */
    @Override
    public JSONObject searchFlightByNumber(String flightNo, Integer page, Integer pageSize) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("flight_no", "arrive_airport", "departure_time");
        wrapper.like("flight_no", flightNo);
        IPage<Flight> list = flightMapper.selectPage(new Page<>(page, pageSize), wrapper);
        List<QueryByFlightNoVO> queryByFlightNoVOList = new ArrayList<>();
        for (int i = 0; i < list.getRecords().size(); i++) {
            QueryByFlightNoVO queryByFlightNoVO = new QueryByFlightNoVO();
            queryByFlightNoVO.setFlightNo(list.getRecords().get(i).getFlightNo())
                    .setArriveAirport(list.getRecords().get(i).getArriveAirport())
                    .setDepartureTime(list.getRecords().get(i).getDepartureTime());

            queryByFlightNoVOList.add(i, queryByFlightNoVO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("list", queryByFlightNoVOList);
        jsonObject.append("total", list.getTotal());
        return jsonObject;
    }

    /**
     * 搜索航班
     *
     * @param province1         province1
     * @param province2         province2
     * @param date              日期
     * @param page              页面
     * @param pageSize          页面大小
     * @param shippingSpaceType 舱位类型
     * @param screenType        屏幕类型
     * @return {@link List}<{@link QueryFlightVO}>
     */
    @Override
    public JSONObject searchFlight(String province1, String province2, String date, Integer page, Integer pageSize, Integer shippingSpaceType, Integer screenType) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("flight_type", "flight_no", "departure_airport", "arrive_airport",
                "departure_time", "arrive_time", "seat_f_price", "seat_y_price");
        wrapper.eq("departure_city", province1);
        wrapper.eq("arrive_city", province2);
        wrapper.like("departure_time", date);
        // 舱位不限
        if (shippingSpaceType == 1) {
            wrapper.gt("seat_y_vacant", 0);
            //wrapper.gt("seat_f_vacant", 0);
            // 建议：先查经济舱有座位余量的，没有经济舱了再查头等舱，否则当头等舱空了就找不到了
        }
        // 经济舱
        if (shippingSpaceType == 2) {
            wrapper.gt("seat_y_vacant", 0);
        }
        // 头等舱
        if (shippingSpaceType == 3) {
            wrapper.gt("seat_f_vacant", 0);
        }

        // 价格升降
        if (screenType == 2) { // 低到高
            if (shippingSpaceType != 3) { // 经济舱
                wrapper.orderByAsc("seat_y_price");
            } else {    // 头等舱
                wrapper.orderByAsc("seat_f_price");
            }
        }
        if (screenType == 3) { // 高到低
            if (shippingSpaceType != 3) { // 经济舱
                wrapper.orderByDesc("seat_y_price");
            } else {    // 头等舱
                wrapper.orderByDesc("seat_f_price");
            }
        }
        // 时间升降
        if (screenType == 5) { // 低到高
            wrapper.orderByAsc("departure_time");
        }
        if (screenType == 6) { // 高到低
            wrapper.orderByDesc("departure_time");
        }
        IPage<Flight> list = flightMapper.selectPage(new Page<>(page, pageSize), wrapper);
        List<QueryFlightVO> queryFlightVOList = new ArrayList<>();
        for (int i = 0; i < list.getRecords().size(); i++) {
            QueryFlightVO queryFlightVO = new QueryFlightVO();
            queryFlightVO.setFlightType(list.getRecords().get(i).getFlightType())
                    .setFlightNo(list.getRecords().get(i).getFlightNo())
                    .setDepartureAirport(list.getRecords().get(i).getDepartureAirport())
                    .setArriveAirport(list.getRecords().get(i).getArriveAirport())
                    .setDepartureTime(list.getRecords().get(i).getDepartureTime())
                    .setArriveTime(list.getRecords().get(i).getArriveTime())
                    .setSeatFPrice(list.getRecords().get(i).getSeatFPrice())
                    .setSeatYPrice(list.getRecords().get(i).getSeatYPrice())
                    .setFlightDuration(datetimeSub.subtract(list.getRecords().get(i).getDepartureTime(), list.getRecords().get(i).getArriveTime())); // 将两个时间戳相减

            queryFlightVOList.add(i, queryFlightVO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("list", queryFlightVOList);
        jsonObject.append("total", list.getTotal());
        return jsonObject;
    }
}
