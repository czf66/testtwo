package com.flight.order.controller;

import com.flight.base.util.JwtUtils;
import com.flight.base.util.R;
import com.flight.order.bean.dto.AddOrderDTO;
import com.flight.order.bean.dto.DelOrderDTO;
import com.flight.order.bean.dto.SeatChangeDTO;
import com.flight.order.bean.dto.TicketChangeDTO;
import com.flight.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@RestController
@RequestMapping("/flygo")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    @ApiOperation("测试")
    public R test(@ApiIgnore HttpServletRequest request) {
        if (JwtUtils.checkToken(request)) {
            return R.data(JwtUtils.getMemberByJwtToken(request));
        }
        return R.error();
    }

    @PostMapping("/placeAnOrder")
    @ApiOperation("创建订单")
    @CrossOrigin(origins = "*")
    public R placeAnOrder(@Validated @RequestBody AddOrderDTO addOrderDTO, @ApiIgnore HttpServletRequest request) {
        System.out.println(addOrderDTO);
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        addOrderDTO.setUserId(Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId"))));
//        addOrderDTO.setUserId(1L);
        Integer result = orderService.placeAnOrder(addOrderDTO);
        if (result == 1) {
            return R.ok();
        }
        if (result == 2) {
            return R.error("此航班的经济舱已售完！");
        }
        if (result == 3) {
            return R.error("此航班的头等舱已售完！");
        }
        if (result == 4) {
            return R.error("插入订单失败");
        }
        return R.error();
    }


    @GetMapping("/queryOrderByUserId/{page}/{pageSize}/{queryType}")
    @ApiOperation("用户查询订单")
    @CrossOrigin(origins = "*")
    public R queryOrderByUserId(@PathVariable Integer page,
                                @PathVariable Integer pageSize,
                                @PathVariable Integer queryType,
                                @ApiIgnore HttpServletRequest request) {
//        System.out.println("======"+JwtUtils.checkToken(request));
//        System.out.println(request.getHeader("token"));
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        System.out.println("======="+Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId"))));
        Long userId = Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")));
        if (userId == null) {
            return R.error("用户未登录，授权失败");
        }
        return R.data(orderService.queryOrderByUserId(userId, page, pageSize, queryType));
    }

    @PostMapping("/returnTicket")
    @ApiOperation("用户退票")
    public R returnTicket(@RequestBody DelOrderDTO delOrderDTO, @ApiIgnore HttpServletRequest request) throws ParseException {
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        Long userId = Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")));
        delOrderDTO.setUserId(userId);
        if (orderService.returnTicket(delOrderDTO)) {
            return R.ok();
        }
        return R.error("不能在出发当天退票");
    }

    @GetMapping("/getEmptySeat/{flightNo}/{shippingSpace}")
    @ApiOperation("用户获取空座位")
    public R getEmptySeat(@PathVariable String flightNo, @PathVariable Integer shippingSpace, @ApiIgnore HttpServletRequest request) {
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        return R.data(orderService.getEmptySeat(flightNo, shippingSpace));
    }

    @PostMapping("/seatChange")
    @ApiOperation("用户换座位")
    public R seatChange(@Validated @RequestBody SeatChangeDTO seatChangeDTO, @ApiIgnore HttpServletRequest request) {
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        Long userId = Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")));
        if (userId == null) {
            return R.error("用户未登录，授权失败");
        }
        seatChangeDTO.setUserId(userId);
        if (orderService.seatChange(seatChangeDTO)) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/ticketChange")
    @ApiOperation("用户改签")
    public R ticketChange(@Validated @RequestBody TicketChangeDTO ticketChangeDTO, @ApiIgnore HttpServletRequest request) {
        if (!JwtUtils.checkToken(request)) {
            return R.error("用户未登录，授权失败");
        }
        Long userId = Long.valueOf(String.valueOf(JwtUtils.getMemberByJwtToken(request).get("userId")));
        ticketChangeDTO.setUserId(userId);
        if (orderService.ticketChange(ticketChangeDTO)) {
            return R.ok();
        }
        return R.error();
    }
}
