package com.exam.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exam.bean.CarData;
import com.exam.bean.CarList;
import com.exam.model.MyModel;
import com.exam.shop.R;
import com.exam.utils.AddSubLayout;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends BaseExpandableListAdapter {
//    private List<CarList> listgoods = new ArrayList<>();
    private List<CarData> listshop = new ArrayList<>();
    private TotalPriceListener totalPriceListener;
    private  Context context;
    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }

    public CarAdapter(Context context) {
        this.context = context;
    }

    public void setListshop(List<CarData> listshop){
        this.listshop = listshop;
    }
    public List<CarData> getListshop( ){
        return listshop;
    }
    @Override
    public int getGroupCount() {
        return listshop.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<CarList> list = listshop.get(groupPosition).getList();
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listshop.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listshop.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
         MyHolder2 myHolder = null;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.groupitem,null);
            myHolder = new MyHolder2();
            myHolder.checkBox = convertView.findViewById(R.id.group_box);
            convertView.setTag(myHolder);
        }else {
            myHolder = (MyHolder2)convertView.getTag();
        }
        final CarData carData = listshop.get(groupPosition);
        String sellerName = listshop.get(groupPosition).getSellerName();
        myHolder.checkBox.setText(carData.getSellerName());
        myHolder.checkBox.setChecked(carData.isCheck());//设置商铺选中状态
        myHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                carData.setCheck(checked);//数据更新
                List<CarList> carLists = listshop.get(groupPosition).getList();//得到商品信息
                for (int i = 0; i < carLists.size(); i++) {//商品信息循环赋值
                    carLists.get(i).setSelected(checked?1:0);//商铺选中则商品必须选中
                }
                notifyDataSetChanged();
                //计算价格
                calculatePrice();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyHolder1 myHodler = null;
        if (convertView == null){
            convertView = View.inflate(context,R.layout.childitem,null);
            myHodler = new MyHolder1();
            myHodler.price =convertView.findViewById(R.id.child_price);
            myHodler.image = convertView.findViewById(R.id.child_image);
            myHodler.text = convertView.findViewById(R.id.child_text);
            myHodler.addSub = convertView.findViewById(R.id.child_add_aa);
            myHodler.check = convertView.findViewById(R.id.child_box);
            convertView.setTag(myHodler);
        }else{
            myHodler = (MyHolder1)convertView.getTag();
        }
        final CarList carList = listshop.get(groupPosition).getList().get(childPosition);
        myHodler.addSub.setCount(carList.getNum());
        String images = carList.getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(myHodler.image);
        myHodler.price.setText(""+carList.getPrice());
        String title = carList.getTitle();
        myHodler.text.setText(title);
        myHodler.addSub.setCount(carList.getNum());//设置商品数量
        myHodler.addSub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                carList.setNum(count);
                calculatePrice();//计算价格
            }
        });

        myHodler.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean checked = checkBox.isChecked();
                carList.setSelected(checked?1:0);
                calculatePrice();//计算价格

            }
        });
        if (carList.getSelected()==0){
            myHodler.check.setChecked(false);
        }else{
            myHodler.check.setChecked(true);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public void checkAll(boolean isCheck){
        for (int i = 0; i < listshop.size(); i++) {//循环的商家
            CarData carData = listshop.get(i);
            carData.setCheck(isCheck);
            for (int j = 0; j < carData.getList().size(); j++) {
                CarList carList = carData.getList().get(j);
                carList.setSelected(isCheck?1:0);
            }
        }
        notifyDataSetChanged();
        calculatePrice();
    }
    private void calculatePrice(){
        double totalPrice=0;
        for (int i = 0; i < listshop.size(); i++) {//循环的商家
            CarData carData = listshop.get(i);
            for (int j = 0; j < carData.getList().size(); j++) {
                CarList carList = carData.getList().get(j);
                if (carList.getSelected()==1) {//如果是选中状态
                    totalPrice = totalPrice + carList.getNum() * carList.getPrice();
                }
            }
        }
        Log.e("LL","计算完的数据"+totalPrice);
        if (totalPriceListener!=null) {
            Log.e( "LL",totalPrice + "传进接口");
            totalPriceListener.totalPrice(totalPrice);
        }

    }
    class MyHolder1{
        CheckBox check;
        TextView text;
        TextView price;
        ImageView image;
        AddSubLayout addSub;
    }
    class MyHolder2{
        CheckBox checkBox;
    }

    public interface TotalPriceListener{
        void totalPrice(double totalPrice);
    }
}
