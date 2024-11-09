package com.flight.user.feign.factory;

import com.flight.user.feign.RemoteUserService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable throwable) {
        return new RemoteUserService() {
//            @Override
//            public List<StudentDetailedVO> queryStudentByClassId(Long classId) {
//                return null;
//            }
        };
    }
}
