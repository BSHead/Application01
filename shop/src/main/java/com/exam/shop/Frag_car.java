package com.exam.shop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.adapter.CarAdapter;
import com.exam.bean.CarData;
import com.exam.core.BaseCar;
import com.exam.presenter.Mypresenter;
import java.util.List;

public class Frag_car extends Fragment implements BaseCar{

    private Mypresenter mypresenter;
    private ExpandableListView listView;
    private CarAdapter carAdapter;
    private TextView mSumPrice;
    private CheckBox mCheckAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_car,container,false);
        initdata();
        listView = view.findViewById(R.id.xlv);
        carAdapter = new CarAdapter(getActivity());
        mSumPrice = view.findViewById(R.id.goods_sum_price);
        mCheckAll = view.findViewById(R.id.check_all);
        mCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carAdapter.checkAll(isChecked);
            }
        });
        carAdapter.setTotalPriceListener(new CarAdapter.TotalPriceListener() {
            @Override
            public void totalPrice(double totalPrice) {
                String s = String.valueOf(totalPrice);
                Log.e(totalPrice+"这是反回来的数据",s);
                mSumPrice.setText(s);
            }
        });
        listView.setAdapter(carAdapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        return view;
    }

    private void initdata() {
        mypresenter = new Mypresenter((BaseCar)this);
        mypresenter.getCarData();
    }

    @Override
    public void Success(List<CarData> data) {
      //  Toast.makeText(getActivity(),""+data.size(),Toast.LENGTH_SHORT).show();
        carAdapter.setListshop(data);

        for (int i=0; i<data.size(); i++)
        {
            listView.expandGroup(i);
        };
    }

}