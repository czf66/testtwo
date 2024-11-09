package com.flight.query.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flight.query.domain.Flight;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Mapper
public interface FlightMapper extends BaseMapper<Flight> {
}
