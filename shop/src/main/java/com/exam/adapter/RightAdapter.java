package com.exam.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.exam.bean.CarList;
import com.exam.shop.R;
import com.exam.utils.AddSubLayout;
import java.util.ArrayList;
import java.util.List;

public class RightAdapter extends BaseAdapter {
    private List<CarList> mList = new ArrayList<>();
    private Context context;

    public RightAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getAll(List<CarList> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }
    public void getclear(){
        mList.clear();
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        RightHolder rightHolder =null;
        if (convertView == null){
            convertView = View.inflate(context,R.layout.rightitem,null);
            rightHolder = new RightHolder();
            rightHolder.text = convertView.findViewById(R.id.rignt_text);
            rightHolder.price =convertView.findViewById(R.id.rignt_price);
            rightHolder.image = convertView.findViewById(R.id.rignt_image);
            rightHolder.addSub = convertView.findViewById(R.id.rignt_add_aa);
            convertView.setTag(rightHolder);
        }{
            rightHolder = (RightHolder)convertView.getTag();
        }
       // Log.e("II",mList.get(position).getTitle());
        final CarList carList1 = mList.get(position);
        rightHolder.text.setText(carList1.getTitle());
        rightHolder.price.setText(carList1.getPrice()+"");
        String images = carList1.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(rightHolder.image);
        rightHolder.addSub.setCount(carList1.getNum());//设置商品数量

        rightHolder.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                carList1.setNum(count);
                onNumListener.onNum();
            }
        });
        return convertView;
    }
    class RightHolder{
        TextView text;
        TextView price;
        ImageView image;
        AddSubLayout addSub;
    }
    private OnNumListener onNumListener;

    public void setOnNumListener(OnNumListener onNumListener) {
        this.onNumListener = onNumListener;
    }

    public interface OnNumListener{
        void onNum();
    }
}