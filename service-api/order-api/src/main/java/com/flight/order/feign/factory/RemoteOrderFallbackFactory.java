package com.flight.order.feign.factory;

import com.flight.order.feign.RemoteOrderService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Component
public class RemoteOrderFallbackFactory implements FallbackFactory<RemoteOrderService> {
    @Override
    public RemoteOrderService create(Throwable throwable) {
        return new RemoteOrderService() {
//            @Override
//            public List<StudentDetailedVO> queryStudentByClassId(Long classId) {
//                return null;
//            }
        };
    }
}
