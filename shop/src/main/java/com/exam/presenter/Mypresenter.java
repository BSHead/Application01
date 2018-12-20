package com.exam.presenter;

import android.os.AsyncTask;
import android.util.Log;


import com.exam.bean.CarData;
import com.exam.bean.CarJson;
import com.exam.bean.Data;
import com.exam.bean.DataBean;
import com.exam.core.BaseCar;
import com.exam.core.BaseNetData;
import com.exam.model.MyModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Mypresenter {
    private BaseCar baseCar;
    private BaseNetData baseNetData;
    public Mypresenter(BaseNetData baseNetData) {
        this.baseNetData = baseNetData;
    }

    public Mypresenter(BaseCar baseCar) {
        this.baseCar = baseCar;
    }
    /*    public void unBindNetData(BaseNetData baseNetData){
        this.baseNetData = null;
    }*/

    public void getNetData(String name,int i){

        String Strurl = "http://www.zhaoapi.cn/product/searchProducts?keywords="+name+"&page="+i;

        new MyAsyncTask().execute(Strurl);
    }
    class MyAsyncTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String dataJaon = MyModel.getDataJaon(strings[0]);
            return dataJaon;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            DataBean netData = gson.fromJson(s, DataBean.class);
            ArrayList<Data> data = netData.getData();
           // Log.e("这是返回来的数据",""+data.toString());
            baseNetData.Success(data);
        }
    }
    public void getCarData(){

        String Strurl = "http://www.zhaoapi.cn/product/getCarts?uid=71";

        new MyAsyncTask1().execute(Strurl);
    }
    class MyAsyncTask1 extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String dataJaon = MyModel.getDataJaon(strings[0]);
            return dataJaon;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            CarJson carJson = gson.fromJson(s, CarJson.class);
            List<CarData> data = carJson.getData();
            baseCar.Success(data);
        }
    }
}
