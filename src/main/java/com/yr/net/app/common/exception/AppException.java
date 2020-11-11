package com.yr.net.app.common.exception;

/**
 * 系统内部异常
 *
 * @author dengbp
 */
public class AppException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public AppException(String message) {
        super(message);
    }
}
