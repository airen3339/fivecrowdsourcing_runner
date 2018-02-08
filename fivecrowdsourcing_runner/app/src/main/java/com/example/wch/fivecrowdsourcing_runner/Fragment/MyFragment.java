package com.example.wch.fivecrowdsourcing_runner.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wch.fivecrowdsourcing_runner.Gson.Me;
import com.example.wch.fivecrowdsourcing_runner.Interface.AfterLogin;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Util.CacheData;
import com.example.wch.fivecrowdsourcing_runner.Util.DealData;

/**
 * Created by zc on 2017/8/3.
 */

public class MyFragment extends Fragment {
    Context mContext;
    View rootView;

    TextView tvName;
    TextView tvAccount;
    TextView tvPhone;
    TextView tvCount;
    RelativeLayout rlRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_my, container, false);
            mContext = getActivity();
            tvAccount = (TextView) rootView.findViewById(R.id.tv_account);
            tvName = (TextView) rootView.findViewById(R.id.tv_name);
            tvCount = (TextView) rootView.findViewById(R.id.tv_count);
            tvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
            rlRefresh = (RelativeLayout) rootView.findViewById(R.id.rl_refresh);
            setListener();
            setInfo();
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        return rootView;
    }

    public void setListener() {
        rlRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(mContext);
                dialog.show();
                DealData.getMe(CacheData.getMe().getAccount(), CacheData.getMe().getPassword(), new AfterLogin() {
                    @Override
                    public void onSuccess() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setInfo();
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String msg) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(mContext, "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }


    private void setInfo() {
        Me me = CacheData.getMe();
        tvName.setText(me.getName());
        tvPhone.setText(me.getPhone());
        tvCount.setText(Integer.toString(me.getCount()));
        tvAccount.setText(me.getAccount());
    }

}
