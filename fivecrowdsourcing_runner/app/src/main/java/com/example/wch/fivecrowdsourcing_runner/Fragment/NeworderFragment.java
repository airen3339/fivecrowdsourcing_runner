package com.example.wch.fivecrowdsourcing_runner.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wch.fivecrowdsourcing_runner.Adapter.OrderAdapter;
import com.example.wch.fivecrowdsourcing_runner.Util.MyApplication;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Util.CacheData;

/**
 * Created by zc on 2017/8/3.
 */

public class NeworderFragment extends Fragment {
    /*控制变量*/
    Context mContext;
    View rootView;
    RecyclerView rvNeworder;
    OrderAdapter adapter;
    NeworderBroadcast broadcast;
    IntentFilter intentFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_neworder, container, false);
            mContext = getActivity();
            findView();
            setAdapter();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        broadcast = new NeworderBroadcast();
        intentFilter = new IntentFilter(MyApplication.BROADCAST_NEW_UNACCEPTED);
        mContext.registerReceiver(broadcast, intentFilter);
    }

    @Override
    public void onStop() {
        mContext.unregisterReceiver(broadcast);
        super.onStop();
    }

    final private void findView() {
        rvNeworder = (RecyclerView) rootView.findViewById(R.id.rv_neworder);
    }

    final private void setAdapter() {
        adapter = new OrderAdapter(mContext, CacheData.getNeworderList());
        rvNeworder.setLayoutManager(new LinearLayoutManager(mContext));
        rvNeworder.setAdapter(adapter);
    }

    class NeworderBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    }
}
