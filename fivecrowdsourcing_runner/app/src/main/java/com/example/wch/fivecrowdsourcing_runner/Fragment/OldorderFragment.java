package com.example.wch.fivecrowdsourcing_runner.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wch.fivecrowdsourcing_runner.Adapter.OrderAdapter;
import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Util.CacheData;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by zc on 2017/8/3.
 */

public class OldorderFragment extends Fragment {
    private int showCount = 0;
    Context mContext;
    View rootView;
    RecyclerView rvOldorder;
    OrderAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_oldorder, container, false);
            mContext = getActivity();
            findView();
            setListener();
            init();
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        return rootView;
    }

    private void findView() {
        rvOldorder = (RecyclerView) rootView.findViewById(R.id.rv_oldorder);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findOrder();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void init() {
        adapter = new OrderAdapter(mContext, CacheData.getOldorderList());
        rvOldorder.setLayoutManager(new LinearLayoutManager(mContext));
        rvOldorder.setAdapter(adapter);
    }

    private void findOrder() {
        ArrayList<OrderInfo> oldList = CacheData.getOldorderList();
        ArrayList<OrderInfo> orderInfoList = (ArrayList<OrderInfo>) DataSupport.
                limit(5)
                .offset(oldList.size())
                .find(OrderInfo.class);
        if (orderInfoList.size() > 0) {
            synchronized (oldList) {
                oldList.addAll(orderInfoList);
            }
        } else {
            Toast.makeText(mContext, "没有更多信息了", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}
