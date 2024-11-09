package com.flight.order.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.flight.base.util.BaseMyBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Configuration
@MapperScan("com.flight.order.mapper")   // 扫描mapper文件夹
@EnableTransactionManagement    //自动管理事务
public class MyBatisPlusConfig extends BaseMyBatisPlusConfig implements MetaObjectHandler {}
