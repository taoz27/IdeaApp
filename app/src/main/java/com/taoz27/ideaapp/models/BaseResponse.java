package com.taoz27.ideaapp.models;

import java.io.Serializable;

/**
 * Created by taoz27 on 2017/11/17.
 */

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 638133706259127233L;

    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
