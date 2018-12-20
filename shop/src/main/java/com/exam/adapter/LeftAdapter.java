package com.exam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exam.bean.CarData;
import com.exam.shop.R;

import java.util.ArrayList;
import java.util.List;

public class LeftAdapter extends BaseAdapter {
    private List<CarData> mList = new ArrayList<>();
    private Context context;

    public LeftAdapter(Context context) {
        this.context = context;
    }
    public void getAll(List<CarData> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }
    public List<CarData> getList(){
        return mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftHolder leftHolder =null;
        if (convertView == null){
            convertView = View.inflate(context,R.layout.leftitem,null);
            leftHolder = new LeftHolder();
            leftHolder.textView = convertView.findViewById(R.id.lefttext);

            convertView.setTag(leftHolder);
        }else {
            leftHolder = (LeftHolder)convertView.getTag();
        }

        String sellerName = mList.get(position).getSellerName();
        leftHolder.textView.setText(sellerName);
        return convertView;
    }
    class LeftHolder{
        TextView textView;
    }
}
