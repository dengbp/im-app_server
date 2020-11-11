package com.yr.net.app.common.function;


import com.yr.net.app.common.exception.RedisConnectException;

/**
 * @author dengbp
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
