package com.exam.shop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class MainActivity extends AppCompatActivity  {


    private FragmentManager fragmentManager;
    private Frag_home frag_home;
    private Frag_car frag_car;
    private RadioGroup group;
    private Frag_menu frag_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        group = findViewById(R.id.group);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        frag_home = new Frag_home();
        frag_car = new Frag_car();
        frag_menu = new Frag_menu();
        fragmentTransaction.add(R.id.main_frag,frag_home);
        fragmentTransaction.add(R.id.main_frag,frag_menu);
        fragmentTransaction.add(R.id.main_frag,frag_car);
        fragmentTransaction.hide(frag_menu);
        fragmentTransaction.hide(frag_car);
        fragmentTransaction.commit();
        group.check(RadioGroup.AUTOFILL_TYPE_TEXT);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.hide(frag_menu);
                transaction.hide(frag_home);
                transaction.hide(frag_car);
                switch (checkedId){
                    case 1:
                        transaction.show(frag_home);
                        break;
                    case 2:
                        transaction.show(frag_menu);
                        break;
                    case 3:
                        transaction.show(frag_car);
                        break;
                }
                transaction.commit();
            }
        });
    }
}