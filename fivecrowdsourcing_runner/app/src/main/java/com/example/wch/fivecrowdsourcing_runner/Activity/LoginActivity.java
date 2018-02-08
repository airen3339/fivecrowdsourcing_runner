package com.example.wch.fivecrowdsourcing_runner.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wch.fivecrowdsourcing_runner.Activity.SuperActivity.BaseActivity;
import com.example.wch.fivecrowdsourcing_runner.Interface.AfterLogin;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Util.DealData;

public class LoginActivity extends BaseActivity {
    Context mContext = LoginActivity.this;
    Button btnLogin;
    EditText etAccount;
    EditText etPassword;
    String strAccount;
    String strPassword;
    //actionBar 的控件
    TextView tvBack;//返回按钮
    TextView tvTitle;//标题按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        setListener();
        init();
    }

    final private void findView() {
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    final private void setListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strAccount = etAccount.getText().toString().trim();
                strPassword = etAccount.getText().toString().trim();
                if ("".equals(strAccount) || "".equals(strPassword)) {
                    Toast.makeText(mContext, "请检查信息是否填写完整", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("正在登录...");
                    progressDialog.show();
                    btnLogin.setEnabled(false);
                    DealData.getMe(strAccount, strPassword, new AfterLogin() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                    btnLogin.setEnabled(true);
                                    progressDialog.dismiss();
                                }
                            });
                        }

                        @Override
                        public void onFailure(final String msg) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (msg != null) {
                                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                                    }
                                    btnLogin.setEnabled(true);
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    final private void init() {
        tvTitle.setText("登录账号");
        tvBack.setVisibility(View.GONE);
    }
}
