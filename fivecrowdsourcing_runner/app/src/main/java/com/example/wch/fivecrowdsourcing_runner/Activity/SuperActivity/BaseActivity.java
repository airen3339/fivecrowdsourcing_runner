package com.example.wch.fivecrowdsourcing_runner.Activity.SuperActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    /*控件*/
    ProgressDialog progressDialog;

    /*控制变量*/
    Context mContext = BaseActivity.this;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 显示加载框
     */
    protected void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("正在加载...");
        }
        progressDialog.show();
    }

    /**
     * 隐藏加载框
     */
    protected void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
