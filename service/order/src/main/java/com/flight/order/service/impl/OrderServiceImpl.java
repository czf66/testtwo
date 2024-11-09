package com.flight.order.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flight.order.bean.dto.AddOrderDTO;
import com.flight.order.bean.dto.DelOrderDTO;
import com.flight.order.bean.dto.SeatChangeDTO;
import com.flight.order.bean.dto.TicketChangeDTO;
import com.flight.order.bean.vo.OrderInfoVO;
import com.flight.order.domain.Flight;
import com.flight.order.domain.Orders;
import com.flight.order.mapper.FlightMapper;
import com.flight.order.mapper.OrderMapper;
import com.flight.order.service.OrderService;
import com.flight.order.util.SeatChange;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 宇宙超级无敌大帅哥罗伟汶
 * @since 2022/10/18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private FlightMapper flightMapper;

    @Autowired
    private SeatChange seatChange;

    /**
     * 创建订单
     *
     * @param addOrderDTO 添加订单dto
     * @return {@link Integer}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer placeAnOrder(AddOrderDTO addOrderDTO) {
//        Config config = new Config();
//        config.useClusterServers()
//                .addNodeAddress("redis://1.12.246.81:6379");
//        String lockId = UUID.randomUUID().toString(); // 密码锁
//        RedissonClient redissonClient = Redisson.create(config);
//        RLock redissonLock = redissonClient.getLock("Lwwwwaaa");// 获取锁
        try {
//            redissonLock.lock(3, TimeUnit.SECONDS);// 上锁
            // 生成订单号
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String now = df.format(System.currentTimeMillis());
            String random = String.valueOf((int)((Math.random()*9+1)*100000));

            // 添加订单实体
            Orders order = new Orders();
            order.setUserId(addOrderDTO.getUserId())
                    .setFlightNo(addOrderDTO.getFlightNo())
                    .setDepartureTime(addOrderDTO.getDepartureTime())
                    .setDepartureCity(addOrderDTO.getDepartureCity())
                    .setArriveTime(addOrderDTO.getArriveTime())
                    .setArriveCity(addOrderDTO.getArriveCity())
                    .setShippingSpace(addOrderDTO.getShippingSpace())
                    .setPrice(addOrderDTO.getPrice())
                    .setOrderNo(now + random);

            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("flight_no", order.getFlightNo());
            wrapper.select("seat_f_vacant", "seat_y_vacant");
            Flight flight = flightMapper.selectOne(wrapper);

            // 随机生成座位
            wrapper.clear();
            wrapper.eq("flight_no", addOrderDTO.getFlightNo());
            if (order.getShippingSpace() == 1) { // 经济舱
                wrapper.eq("shipping_space", 1);
            } else { // 头等舱
                wrapper.eq("shipping_space", 2);
            }
            wrapper.select("seat");
            List<Orders> ordersList = orderMapper.selectList(wrapper);
            List<String> seats = new ArrayList<>();// 已有座位
            for (int i = 0; i < ordersList.size(); i++) {
                seats.add(ordersList.get(i).getSeat());
            }
            List<String> newSeat = seatChange.getSeat(seats, addOrderDTO.getShippingSpace());
            String seat = "";
            for (int i = 0; i < newSeat.size(); i++) {
                if (!newSeat.get(i).equals("0")) {
                    seat = newSeat.get(i);
                }
            }
            order.setSeat(seat);
            Date date = new Date();
            String a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            order.setOrderTime(a);

            // 修改航班表座位数
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("flight_no", addOrderDTO.getFlightNo());
            if (addOrderDTO.getShippingSpace() == 1) { // 经济舱
                if (flight.getSeatYVacant() == 0) {
                    return 2;
                }
                updateWrapper.set("seat_y_vacant", flight.getSeatYVacant() - 1);
            } else { // 头等舱
                if (flight.getSeatFVacant() == 0) {
                    return 3;
                }
                updateWrapper.set("seat_f_vacant", flight.getSeatFVacant() - 1);
            }
            flightMapper.update(null, updateWrapper);
            int result = orderMapper.insert(order);
            if (result == 1) {
                return 1;
            }
            return 4;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();// 回滚
        } finally {
//            redissonLock.unlock();// 解锁
        }
    }

    /**
     * 通过用户id查询订单
     *
     * @param userId    用户id
     * @param page      页面
     * @param pageSize  页面大小
     * @param queryType 查询类型
     * @return {@link List}<{@link OrderInfoVO}>
     */
    @Override
    public JSONObject queryOrderByUserId(Long userId, Integer page, Integer pageSize, Integer queryType) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        wrapper.select("flight_no", "order_no", "order_time", "departure_time",
                "arrive_time", "departure_city", "arrive_city", "shipping_space", "seat", "price");
        SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(System.currentTimeMillis());
        if (queryType == 1) { // 未出行订单
            wrapper.gt("arrive_time", now);
        }
        if (queryType == 2) { // 历史订单
            wrapper.lt("arrive_time", now);
        }
        IPage<Orders> list = orderMapper.selectPage(new Page<>(page, pageSize), wrapper);
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        for (int i = 0; i < list.getRecords().size(); i++) {
            OrderInfoVO orderInfoVO = new OrderInfoVO();
            orderInfoVO.setFlightNo(list.getRecords().get(i).getFlightNo())
                    .setOrderNo(list.getRecords().get(i).getOrderNo())
                    .setOrderTime(list.getRecords().get(i).getOrderTime())
                    .setDepartureTime(list.getRecords().get(i).getDepartureTime())
                    .setArriveTime(list.getRecords().get(i).getArriveTime())
                    .setDepartureCity(list.getRecords().get(i).getDepartureCity())
                    .setArriveCity(list.getRecords().get(i).getArriveCity())
                    .setShippingSpace(list.getRecords().get(i).getShippingSpace())
                    .setSeat(list.getRecords().get(i).getSeat())
                    .setPrice(list.getRecords().get(i).getPrice());

            orderInfoVOList.add(i, orderInfoVO);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("list", orderInfoVOList);
        jsonObject.append("total", list.getTotal());
        return jsonObject;
    }

    /**
     * 用户退票
     *
     * @param delOrderDTO 删除订单dto
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean returnTicket(DelOrderDTO delOrderDTO) {
        try {
            // 获取订单消息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", delOrderDTO.getUserId());
            queryWrapper.eq("order_no", delOrderDTO.getOrderNo());
            queryWrapper.select("departure_time", "flight_no", "shipping_space");
            Orders orders = orderMapper.selectOne(queryWrapper);

            // 判断出行时间是否为今天
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String now = df.format(System.currentTimeMillis()); // 服务器时间
            Date date = df.parse(orders.getDepartureTime());
            String orders_departure_time = df.format(date);

            if (now.equals(orders_departure_time)) {
                return false;// 当天航班不能退票
            }

            // 删除订单
            int result = orderMapper.delete(queryWrapper);
            if (result == 1) {
                // 修改航班座位数量
                queryWrapper.clear();
                queryWrapper.eq("flight_no", orders.getFlightNo());
                queryWrapper.select("seat_f_vacant", "seat_y_vacant");
                Flight flight = flightMapper.selectOne(queryWrapper);

                UpdateWrapper updateWrapper = new UpdateWrapper();
                updateWrapper.eq("flight_no", orders.getFlightNo());
                if (orders.getShippingSpace() == 1) { // 经济舱
                    updateWrapper.set("seat_y_vacant", flight.getSeatYVacant() + 1);// 经济舱座位+1
                } else { // 头等舱
                    updateWrapper.set("seat_f_vacant", flight.getSeatFVacant() + 1);// 头等舱座位+1
                }
                int result__ = flightMapper.update(null, updateWrapper);
                if (result__ == 1) { // 座位数加1成功后执行
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 获取空座位
     *
     * @param flightNo      航班号
     * @param shippingSpace 舱位类型
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> getEmptySeat(String flightNo, Integer shippingSpace) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("flight_no", flightNo);
        if (shippingSpace == 1) { // 经济舱
            wrapper.eq("shipping_space", 1);
        } else { // 头等舱
            wrapper.eq("shipping_space", 2);
        }
        wrapper.select("seat");
        List<Orders> list = orderMapper.selectList(wrapper);
        List<String> seat = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            seat.add(list.get(i).getSeat());
        }
        List<String> newSeat = seatChange.getSeat(seat, shippingSpace);
        return newSeat;
    }

    /**
     * 更换座位
     *
     * @param seatChangeDTO 更换座位dto
     * @return {@link Boolean}
     */
    @Override
    public Boolean seatChange(SeatChangeDTO seatChangeDTO) {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://1.12.246.81:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RLock redissonLock = redissonClient.getLock("Lwwwwaaa");// 获取锁
        try {
            redissonLock.lock(2, TimeUnit.SECONDS);// 上锁

            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("user_id", seatChangeDTO.getUserId());
            updateWrapper.eq("order_no", seatChangeDTO.getOrderNo());
            updateWrapper.set("seat", seatChangeDTO.getSeat());
            int result = orderMapper.update(null, updateWrapper);
            if (result == 1) {
                return true;
            }
            return false;
        } finally {
            redissonLock.unlock();// 解锁
        }
    }

    /**
     * 用户改签
     *
     * @param ticketChangeDTO 用户改签dto
     * @return {@link Boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean ticketChange(TicketChangeDTO ticketChangeDTO) {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://1.12.246.81:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RLock redissonLock = redissonClient.getLock("Lwwwwaaa");// 获取锁
        try {
            redissonLock.lock(2, TimeUnit.SECONDS);// 上锁
            // 查询老订单
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", ticketChangeDTO.getUserId());
            queryWrapper.eq("order_no", ticketChangeDTO.getOrderNo());
            queryWrapper.select("flight_no", "shipping_space");
            Orders order = orderMapper.selectOne(queryWrapper);
            String oldFlight_no = order.getFlightNo(); // 被改的飞机编号
            Integer oldShipping_space = order.getShippingSpace(); // 被改的飞机舱位类型

            // 分配座位
            queryWrapper.clear();
            queryWrapper.eq("flight_no", ticketChangeDTO.getFlightNo());
            queryWrapper.select("seat");
            List<Orders> list = orderMapper.selectList(queryWrapper);
            List<String> seats = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                seats.add(list.get(i).getSeat());
            }
            List<String> newSeat = seatChange.getSeat(seats, ticketChangeDTO.getShippingSpace());
            String seat = "";
            for (int i = 0; i < newSeat.size(); i++) {
                if (!newSeat.get(i).equals("0")) {
                    seat = newSeat.get(i);
                }
            }

            // 更新老订单
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("user_id", ticketChangeDTO.getUserId());
            updateWrapper.eq("order_no", ticketChangeDTO.getOrderNo());
            updateWrapper.set("flight_no", ticketChangeDTO.getFlightNo());
            updateWrapper.set("departure_time", ticketChangeDTO.getDepartureTime());
            updateWrapper.set("arrive_time", ticketChangeDTO.getArriveTime());
            updateWrapper.set("shipping_space", ticketChangeDTO.getShippingSpace());
            updateWrapper.set("seat", seat);
            updateWrapper.set("price", ticketChangeDTO.getPrice());

            // 查找航班座位数
            queryWrapper.clear();
            queryWrapper.eq("flight_no", ticketChangeDTO.getFlightNo());
            queryWrapper.select("seat_f_vacant", "seat_y_vacant");
            Flight flight = flightMapper.selectOne(queryWrapper);
            int result = orderMapper.update(null, updateWrapper);// 新订单
            if (result == 1) { // 更新订单成功后执行
                updateWrapper.clear();
                updateWrapper.eq("flight_no", ticketChangeDTO.getFlightNo());
                if (order.getShippingSpace() == 1) { // 经济舱
                    updateWrapper.set("seat_y_vacant", flight.getSeatYVacant() - 1);// 经济舱座位-1
                } else { // 头等舱
                    updateWrapper.set("seat_f_vacant", flight.getSeatFVacant() - 1);// 头等舱座位-1
                }
                int result_ = flightMapper.update(null, updateWrapper);
                if (result_ >= 1) { // 座位数减1成功后执行
                    // 查找老航班座位数
                    queryWrapper.clear();
                    queryWrapper.eq("flight_no", oldFlight_no);
                    queryWrapper.select("seat_f_vacant", "seat_y_vacant");
                    Flight oldFlight = flightMapper.selectOne(queryWrapper);

                    // 更新老航班座位数
                    updateWrapper.clear();
                    updateWrapper.eq("flight_no", oldFlight_no);
                    if (oldShipping_space == 1) { // 经济舱
                        updateWrapper.set("seat_y_vacant", oldFlight.getSeatYVacant() + 1);// 经济舱座位+1
                    } else { // 头等舱
                        updateWrapper.set("seat_f_vacant", oldFlight.getSeatFVacant() + 1);// 头等舱座位+1
                    }
                    int result__ = flightMapper.update(null, updateWrapper);
                    if (result__ >= 1) { // 座位数加1成功后执行
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            redissonLock.unlock();
        }
    }
}
