package com.sendemail.sendemail.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;
    /**
     * 状态信息
     */
    private String message;
    /**
     * 返回结果
     */
    private T data;

    public static<T> ResultBean FAIL(int code, String msg){
        ResultBean<T> bean = new ResultBean<>();
        bean.setCode(code);
        bean.setMessage(msg);
        return bean;
    }

    public static<T> ResultBean FAIL(String msg){
        return FAIL(1, msg);
    }

    public static<T> ResultBean FAIL(){
        return FAIL(1, "");
    }

    public static<T> ResultBean SUCCESS(T t){
        ResultBean<T> bean = new ResultBean<>();
        bean.setCode(0);
        bean.setMessage("成功");
        bean.setData(t);
        return bean;
    }

    public static<T> ResultBean SUCCESS(){
        return SUCCESS(null);
    }

}
