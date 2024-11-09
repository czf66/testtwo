package com.flight.order.feign;

import com.flight.order.feign.factory.RemoteOrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@FeignClient(url = "localhost:8003", name = "ORDER", fallbackFactory = RemoteOrderFallbackFactory.class)
public interface RemoteOrderService {
//    @GetMapping("/student/queryStudentByClassId")
//    List<StudentDetailedVO> queryStudentByClassId(@RequestParam("classId") Long classId);
}
