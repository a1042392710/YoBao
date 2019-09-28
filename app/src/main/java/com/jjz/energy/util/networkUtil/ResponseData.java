package com.jjz.energy.util.networkUtil;

import java.io.Serializable;

/**
 * create by: ch
 * Date: 2018/10/25 上午9:41
 */
public class ResponseData<T> implements Serializable {


    private static final long serialVersionUID = 1L;
    //返回的数据
    private T data;


    //返回状态
//    private StatusBean status;
    private String code;
    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



}
