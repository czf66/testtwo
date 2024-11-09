package com.flight.query.feign;

import com.flight.query.feign.factory.RemoteQueryFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@FeignClient(url = "localhost:8002", name = "QUERY", fallbackFactory = RemoteQueryFallbackFactory.class)
public interface RemoteQueryService {
//    @GetMapping("/student/queryStudentByClassId")
//    List<StudentDetailedVO> queryStudentByClassId(@RequestParam("classId") Long classId);
}
