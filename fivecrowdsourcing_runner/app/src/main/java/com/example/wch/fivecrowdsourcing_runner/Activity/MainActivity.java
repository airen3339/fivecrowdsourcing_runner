package com.example.wch.fivecrowdsourcing_runner.Activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wch.fivecrowdsourcing_runner.Activity.SuperActivity.BaseActivity;
import com.example.wch.fivecrowdsourcing_runner.Fragment.MyFragment;
import com.example.wch.fivecrowdsourcing_runner.Fragment.NeworderFragment;
import com.example.wch.fivecrowdsourcing_runner.Fragment.OldorderFragment;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Service.OrderService;

public class MainActivity extends BaseActivity {
    Context mContext = MainActivity.this;
    TextView tvLeft;
    TextView tvCenter;
    TextView tvRight;


    TextView tvTitle;
    TextView tvBack;
    NeworderFragment neworderFragment;
    OldorderFragment oldorderFragment;
    MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
        init();
    }

    final private void findView() {
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        tvRight = (TextView) findViewById(R.id.tv_right);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    final private void setListener() {
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (neworderFragment == null) {
                    neworderFragment = new NeworderFragment();
                }
                tvLeft.setEnabled(false);
                tvRight.setEnabled(true);
                tvCenter.setEnabled(true);
                tvTitle.setText("未完成订单");
                changeFragment(neworderFragment);
            }
        });
        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldorderFragment == null) {
                    oldorderFragment = new OldorderFragment();
                }
                tvLeft.setEnabled(true);
                tvCenter.setEnabled(false);
                tvRight.setEnabled(true);
                tvTitle.setText("订单记录");
                changeFragment(oldorderFragment);
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myFragment == null) {
                    myFragment = new MyFragment();
                }
                tvLeft.setEnabled(true);
                tvCenter.setEnabled(true);
                tvRight.setEnabled(false);
                tvTitle.setText("我的信息");
                changeFragment(myFragment);
            }
        });
    }

    /**
     * 更换碎片
     *
     * @param fragment
     */
    final private void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_test, fragment);
        transaction.commit();
    }


    final private void init() {
        OrderService.beginService(mContext);//开启与网络连接的服务
        tvLeft.performClick();//自动点击一下第一个碎片按钮
        tvBack.setVisibility(View.GONE);//隐藏返回按钮


    }

}
