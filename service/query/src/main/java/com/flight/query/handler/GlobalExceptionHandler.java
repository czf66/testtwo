package com.flight.query.handler;

import cn.hutool.core.util.StrUtil;
import com.flight.base.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常统一处理
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理 json 请求体调用接口对象参数校验失败抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R jsonParamsException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<Object> errorList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = fieldError.getDefaultMessage();
            errorList.add(msg);
        }
        log.error("校验失败--->"+errorList);
        return R.error(errorList);
    }


    /**
     * 处理单个参数校验失败抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R ParamsException(ConstraintViolationException e) {
        List<Object> errorList = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            StringBuilder message = new StringBuilder();
            Path path = violation.getPropertyPath();
            String[] pathArr = StrUtil.splitToArray(path.toString(), ".");
            String msg = message.append(pathArr[1]).append(violation.getMessage()).toString();
            errorList.add(msg);
        }
        log.error("校验失败--->"+errorList);
        return R.error(errorList);
    }

    /**
     * 处理 form data方式调用接口对象参数校验失败抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public R formDaraParamsException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.error(""+collect);
        return R.error(collect);
    }

    /**
     * 请求方法不被允许异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不被允许异常--->"+e.getMessage());
        return R.error(405,e.getMessage());
    }

    /**
     * Content-Type/Accept 异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("Content-Type/Accept 异常--->"+e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * handlerMapping  接口不存在跑出异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R noHandlerFoundException(NoHandlerFoundException e) {
        log.error("接口不存在跑出异常--->"+e.getMessage());
        return R.error(e.getMessage());
    }


    /**
     * 认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public R UnNoException(AuthenticationException e) {
        log.error("认证异常--->"+e.getMessage());
        return R.error(401,e.getMessage());
    }

    /**
     *未知异常捕获
     */
    @ExceptionHandler(Exception.class)
    public R UnNoException(Exception e) {
        log.error("未知异常捕获--->"+e.getMessage());
        return R.error(500,"未知异常");
    }

}
