package com.example.wch.fivecrowdsourcing_runner.db;

import org.litepal.crud.DataSupport;

/**
 * Created by zc on 2017/8/2.
 */

public class OrderImage extends DataSupport {
    String url;
    byte[] imageBytes;

    public OrderImage(String url, byte[] imageBytes) {
        this.url = url;
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public String getUrl() {
        return url;
    }
}
