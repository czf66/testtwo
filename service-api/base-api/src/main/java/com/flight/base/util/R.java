package com.flight.base.util;

import io.swagger.annotations.ApiModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lwwwwaaa
 * @since 2021-12-24
 */
@ApiModel(value = "R",description = "R")
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = -8157613083634272196L;

    public R() {
        put("code", 200);
        put("msg", "success");
    }

    public static R error() {
        return error(444, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(444, msg);
    }

    public static R error(Object obj) {
        R r = new R();
        r.put("code", 444);
        r.put("data", obj);
        return r;
    }

    public static R error(int code,Object obj) {
        R r = new R();
        r.put("code", code);
        r.put("data", obj);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R data(Object obj) {
        R r = new R();
        r.put("data", obj);
        return r;
    }

    public static R data(Object obj,String msg) {
        R r = new R();
        r.put("data", obj);
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
