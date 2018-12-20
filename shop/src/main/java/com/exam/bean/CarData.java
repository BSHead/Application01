package com.exam.bean;

import java.util.List;

public class CarData {

        /**
         * list : []
         * sellerName :
         * sellerid : 0
         */

        private String sellerName;
        private String sellerid;
        private List<CarList> list;

        private boolean isCheck;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public List<CarList> getList() {
        return list;
    }

    public void setList(List<CarList> list) {
        this.list = list;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}

