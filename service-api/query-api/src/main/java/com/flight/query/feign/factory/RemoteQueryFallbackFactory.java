package com.flight.query.feign.factory;

import com.flight.query.feign.RemoteQueryService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Component
public class RemoteQueryFallbackFactory implements FallbackFactory<RemoteQueryService> {
    @Override
    public RemoteQueryService create(Throwable throwable) {
        return new RemoteQueryService() {
//            @Override
//            public List<StudentDetailedVO> queryStudentByClassId(Long classId) {
//                return null;
//            }
        };
    }
}
