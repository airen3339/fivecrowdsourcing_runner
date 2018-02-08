package com.example.wch.fivecrowdsourcing_runner.Interface;

/**
 * 网络端下载图片
 */

public interface DownImage {
    public void onSuccess(byte[] imageBytes);

    public void onFailure();
}
