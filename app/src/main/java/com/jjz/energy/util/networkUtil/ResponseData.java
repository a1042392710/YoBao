package com.jjz.energy.util.networkUtil;

import java.io.Serializable;

/**
 * create by: xy
 * Date: 2018/10/25 上午9:41
 */
public class ResponseData<T> implements Serializable {


    private static final long serialVersionUID = 1L;
    //返回的数据
    private T data;
    //报文
    private String pack_no;

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

//    public StatusBean getStatus() {
//        return status;
//    }

//    public void setStatus(StatusBean status) {
//        this.status = status;
//    }

    public String getPack_no() {
        return pack_no;
    }

    public void setPack_no(String pack_no) {
        this.pack_no = pack_no;
    }


}
