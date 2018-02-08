package com.example.wch.fivecrowdsourcing_runner.Interface;

import android.support.annotation.Nullable;

import com.example.wch.fivecrowdsourcing_runner.Gson.Me;

/**
 * Created by zc on 2017/8/3.
 */

public interface LoginSender {
    public void onFailure(@Nullable String msg);

    public void onSuccess(Me me);
}
