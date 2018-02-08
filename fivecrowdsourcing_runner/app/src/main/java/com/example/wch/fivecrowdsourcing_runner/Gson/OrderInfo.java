package com.example.wch.fivecrowdsourcing_runner.Gson;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单类
 */

public class OrderInfo extends DataSupport implements Serializable {
    private ArrayList<Dish> mDishList;//菜单列表
    private int mTotalPrice;//总价格
    private String mRemark;//备注
    private User mUser;
    private String mOrderId;
    private int mStatus;
    private Sender mSender;
    private String mImageUrl;
    private int mTime;

    /**
     * @param dishList
     * @param countPrice
     * @param remark
     * @param user
     * @param orderId
     * @param status     状态 0:未接受,1:已接受,2:已分配,3:已弃单
     */
    public OrderInfo(ArrayList<Dish> dishList, int countPrice, String remark, User user, String orderId, Sender sender, String imageUrl, int time, int status) {
        mDishList = dishList;
        mTotalPrice = countPrice;
        mRemark = remark;
        mUser = user;
        mOrderId = orderId;
        mSender = sender;
        mStatus = status;
        mImageUrl = imageUrl;
        mTime = time;
    }

    public void setmSender(Sender mSender) {
        this.mSender = mSender;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmDishList(ArrayList<Dish> mDishList) {
        this.mDishList = mDishList;
    }


    public ArrayList<Dish> getmDishList() {
        return mDishList;
    }

    /**
     * @param mStatus 0,1,2,3分别代表 未接受,已接受,已分配,已弃单,-1代表已经成功
     */
    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public void setmTotalPrice(int mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public void setmRemark(String mRemark) {
        this.mRemark = mRemark;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public int getmTotalPrice() {
        return mTotalPrice;
    }

    public String getmRemark() {
        return mRemark;
    }

    public User getmUser() {
        return mUser;
    }

    public int getmStatus() {
        return mStatus;
    }

    public Sender getmSender() {
        return mSender;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public int getmTime() {
        return mTime;
    }
}

