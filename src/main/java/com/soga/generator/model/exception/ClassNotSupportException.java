package com.soga.generator.model.exception;

/**
 * @description: 类型不支持异常
 * @author: lzr
 * @create: 2021-02-28 00:32
 */
public class ClassNotSupportException extends RuntimeException {

    public ClassNotSupportException(String msg){
        super(msg);
    }
}
