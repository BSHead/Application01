package com.exam.bean;

import java.util.ArrayList;

public class DataBean {
/*
   "code": "0"
           ,"msg": "查询成功",
           "page": "1"
    data[]*/

    private String code;
    private String msg;
    private String page;
    private ArrayList<Data> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
