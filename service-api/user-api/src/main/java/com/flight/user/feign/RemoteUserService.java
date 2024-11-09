package com.flight.user.feign;

import com.flight.user.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@FeignClient(url = "localhost:8001", name = "USER", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {
//    @GetMapping("/student/queryStudentByClassId")
//    List<StudentDetailedVO> queryStudentByClassId(@RequestParam("classId") Long classId);
}
