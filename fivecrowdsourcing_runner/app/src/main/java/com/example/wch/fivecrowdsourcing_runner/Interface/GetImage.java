package com.example.wch.fivecrowdsourcing_runner.Interface;

import android.graphics.Bitmap;

/**
 * Created by zc on 2017/8/2.
 */

public interface GetImage {
    public void onSuccess(Bitmap bitmap);

    public void onFailure();
}
