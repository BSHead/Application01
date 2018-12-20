package com.exam.shop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.exam.adapter.MyRecyclerAdapter;
import com.exam.bean.Data;
import com.exam.core.BaseNetData;
import com.exam.core.OnItemClickListener;
import com.exam.core.OnLongItemClickListener;
import com.exam.model.MyModel;
import com.exam.presenter.Mypresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Frag_home extends Fragment implements BaseNetData,View.OnClickListener{
    int i =1;
    private XRecyclerView recyclerView;
    private Mypresenter mypresenter;
    private EditText text;
    private MyRecyclerAdapter adapter;
    private LinearLayoutManager linerlayoutManager;
    private GridLayoutManager gridLayoutManager;
    private boolean isGrid = true;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home,container,false);

        recyclerView = view.findViewById(R.id.main_recyclerview);
        view.findViewById(R.id.main_image).setOnClickListener(this);
        view. findViewById(R.id.main_but).setOnClickListener(this);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false);
        linerlayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linerlayoutManager);

        adapter = new MyRecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                List<Data> list = adapter.getList();
                Data data = list.get(position-1);
                String s = MyModel.beanToJson(data);
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("item",s);
                startActivity(intent);
            }
        });
        adapter.setOnLongItemClickListener(new OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0,1);
                animator.setDuration(1000);//时间1s
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Toast.makeText(getActivity(),"删除了底"+(i-1)+"条",Toast.LENGTH_SHORT).show();
                        adapter.delList(i);
                        adapter. notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationPause(Animator animation) {
                        super.onAnimationPause(animation);
                    }

                    @Override
                    public void onAnimationResume(Animator animation) {
                        super.onAnimationResume(animation);
                    }
                });
                animator.start();

            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                //Toast.makeText(MainActivity.this,"下拉",Toast.LENGTH_SHORT).show();
                i = 1;
                adapter.clearList();
                initdata(i);
            }

            @Override
            public void onLoadMore() {

                int i1 = i++;

                initdata(i1);
            }
        });
        return view;
    }

    private void initdata(int i) {
        text = view.findViewById(R.id.main_text);
        String string = text.getText().toString();
        mypresenter = new Mypresenter((BaseNetData) this);
        mypresenter.getNetData(string,i);
    }
    //数据成功回调方法
    @Override
    public void Success(ArrayList<Data> data) {
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
        Log.e("---------",data.size()+"");
        adapter.addList(data);
        adapter.notifyDataSetChanged();
    }
    //主页面点击事件
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.main_image){
            adapter.clearList();
            initdata(i);
        }else if (v.getId() == R.id.main_but){
            if(isGrid){
                isGrid = false;
                adapter.setViewType(MyRecyclerAdapter.GRID_TYPE);
                recyclerView.setLayoutManager(gridLayoutManager);
            }else {
                isGrid = true;
                adapter.setViewType(MyRecyclerAdapter.LINEAR_TYPE);
                recyclerView.setLayoutManager(linerlayoutManager);
            }
        }
    }
    //下拉
}
