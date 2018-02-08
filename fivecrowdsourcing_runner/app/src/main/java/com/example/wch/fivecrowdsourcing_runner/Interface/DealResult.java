package com.example.wch.fivecrowdsourcing_runner.Interface;

import android.support.annotation.NonNull;

/**
 * Created by zc on 2017/8/4.
 */

public interface DealResult {
    public void onSuccess();

    public void onFailure(@NonNull String msg);

}
