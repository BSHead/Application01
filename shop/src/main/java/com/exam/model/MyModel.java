package com.exam.model;


import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyModel {
    public static String getDataJaon(String urlstr){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(urlstr).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String beanToJson(Object bean) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(bean);
        System.out.println(jsonStr);
        return jsonStr;
    }
    public static String getCar(String urlstr){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(urlstr).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
