package com.flight.order.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flight.order.bean.dto.AddOrderDTO;
import com.flight.order.bean.dto.DelOrderDTO;
import com.flight.order.bean.dto.SeatChangeDTO;
import com.flight.order.bean.dto.TicketChangeDTO;
import com.flight.order.bean.vo.OrderInfoVO;
import com.flight.order.domain.Orders;

import java.text.ParseException;
import java.util.List;

/**
 * @author Lwwwwaaa
 * @since 2022/10/18
 */
public interface OrderService extends IService<Orders> {
    Integer placeAnOrder(AddOrderDTO addOrderDTO);
    JSONObject queryOrderByUserId(Long userId, Integer page, Integer pageSize, Integer queryType);
    Boolean returnTicket(DelOrderDTO delOrderDTO) throws ParseException;
    List<String> getEmptySeat(String flightNo, Integer shippingSpace);
    Boolean seatChange(SeatChangeDTO seatChangeDTO);
    Boolean ticketChange(TicketChangeDTO ticketChangeDTO);
}
