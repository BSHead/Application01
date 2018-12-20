package com.exam.shop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exam.bean.Data;
import com.exam.bean.DataBean;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class WebActivity extends AppCompatActivity {

    private Banner banner;
    private Data data;
    private TextView textView;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView2);


        Intent intent = getIntent();
        banner = findViewById(R.id.banne);
        String item = intent.getStringExtra("item");
        Gson gson = new Gson();
        data = gson.fromJson(item, Data.class);
        Toast.makeText(this, data.toString(),Toast.LENGTH_SHORT).show();
        ArrayList<String> listu = new ArrayList<>();
        String images = data.getImages();
        String[] split = images.split("\\|");
        for (int i = 0; i < split.length; i++) {
            listu.add(split[i]);
        }
        //放标题的集合


        banner.setImages(listu);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                Glide.with(context).load((String) path).into(imageView);
            }
        });

        banner.setDelayTime(3000);
        banner.start();
        textView.setText(data.getTitle());
        textView1.setText(data.getPrice()+"");
    }
}
