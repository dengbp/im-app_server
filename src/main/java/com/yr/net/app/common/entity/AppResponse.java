package com.yr.net.app.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author dengbp
 */
public class AppResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public AppResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public AppResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public AppResponse data(Object data) {
        this.put("data", data);
        return this;
    }


    public AppResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public AppResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public AppResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
