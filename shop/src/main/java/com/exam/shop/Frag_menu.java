package com.exam.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.adapter.LeftAdapter;
import com.exam.adapter.RightAdapter;
import com.exam.bean.CarData;
import com.exam.bean.CarList;
import com.exam.core.BaseCar;
import com.exam.presenter.Mypresenter;

import java.util.List;

public class Frag_menu extends Fragment implements BaseCar {
    private Mypresenter mypresenter;
    private ListView leftlistView,rigntlistView;
    private LeftAdapter leftAdapter;
    private  RightAdapter rightAdapter;
    private TextView mSumPrice,mCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_menu,container,false);
        leftlistView = view.findViewById(R.id.leftlv);
        rigntlistView = view.findViewById(R.id.rigntlv);
        mSumPrice =  view.findViewById(R.id.menu_sum_price);
        mCount = view.findViewById(R.id.menu_number);
        rightAdapter = new RightAdapter(getActivity());

        initdata();
        rightAdapter.setOnNumListener(new RightAdapter.OnNumListener() {
            @Override
            public void onNum() {
                calculatePrice(leftAdapter.getList());
            }
        });
        return view;
    }
    private void initdata() {
        mypresenter = new Mypresenter((BaseCar)this);
        mypresenter.getCarData();
    }
    @Override
    public void Success(final List<CarData> data) {
        calculatePrice(data);
        leftAdapter = new LeftAdapter(getActivity());
        leftAdapter.getAll(data);
        leftlistView.setAdapter(leftAdapter);
        leftlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<CarList> list = data.get(position).getList();
              //  Log.e("LL",list.size()+"list的条数");
                rightAdapter.getclear();
                rightAdapter.notifyDataSetChanged();
                rightAdapter.getAll(list);
                rigntlistView.setAdapter(rightAdapter);
            }
        });
    }
    private void calculatePrice(List<CarData> shopList){
        double totalPrice=0;
        int totalNum = 0;
        for (int i = 0; i < shopList.size(); i++) {//循环的商家
            CarData carData = shopList.get(i);
            for (int j = 0; j < carData.getList().size(); j++) {
                CarList carList = carData.getList().get(j);
                //计算价格
                totalPrice = totalPrice + carList.getNum() * carList.getPrice();
                totalNum+=carList.getNum();//计数
            }
        }
        mSumPrice.setText("价格："+totalPrice);
        mCount.setText(""+totalNum);
    }
}
