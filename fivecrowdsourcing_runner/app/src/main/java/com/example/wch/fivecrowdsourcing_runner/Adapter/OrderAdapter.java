package com.example.wch.fivecrowdsourcing_runner.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wch.fivecrowdsourcing_runner.Gson.Dish;
import com.example.wch.fivecrowdsourcing_runner.Gson.OrderInfo;
import com.example.wch.fivecrowdsourcing_runner.Gson.User;
import com.example.wch.fivecrowdsourcing_runner.Interface.GetImage;
import com.example.wch.fivecrowdsourcing_runner.R;
import com.example.wch.fivecrowdsourcing_runner.Util.DealData;
import com.example.wch.fivecrowdsourcing_runner.Util.debug;

import java.util.ArrayList;

/**
 * Created by zc on 2017/8/3.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context mContext;
    Activity mActivity;
    /*要显示的数据*/
    ArrayList<OrderInfo> mList;

    public OrderAdapter(Context context, ArrayList<OrderInfo> list) {
        mContext = context;
        mActivity = (Activity) context;
        mList = list;

    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_adapter_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderInfo itemOrderInfo = mList.get(position);
        int totalPrice = itemOrderInfo.getmTotalPrice();
        User user = itemOrderInfo.getmUser();
        ArrayList<Dish> dishList = itemOrderInfo.getmDishList();
        String remark = itemOrderInfo.getmRemark();
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setAdapter(dishList);
        viewHolder.tvName.setText("姓名:" + user.getName());
        viewHolder.tvPhone.setText("电话:" + user.getPhone());
        viewHolder.tvRemark.setText("备注:" + remark);
        viewHolder.tvTotalPrice.setText("总价:" + totalPrice);
        viewHolder.tvPosition.setText("地址:" + user.getPosition());
        DealData.getImage(itemOrderInfo.getmImageUrl(), new GetImage() {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.ivDish.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void onFailure() {
                debug.d("Https", "获取图片信息失败");
            }
        });
        setListener(viewHolder, user.getPhone(), itemOrderInfo);
        adaptShow(itemOrderInfo.getmStatus(), viewHolder);
    }

    public void setListener(ViewHolder viewHolder, final String phone, final OrderInfo orderInfo) {
        /*打电话*/
        viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//判断是否有权限打电话
                    return;
                }
                mContext.startActivity(intent);
            }
        });
        viewHolder.btnArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("确认送达?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DealData.arriveOrder(mActivity, orderInfo);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });

    }

    private void adaptShow(int status, ViewHolder viewHolder) {
        switch (status) {
            case 2:
                break;
            case -1:
                viewHolder.btnArrive.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ListView lvFoodname;
        Button btnCall;
        Button btnArrive;
        TextView tvPosition;
        TextView tvRemark;
        TextView tvName;
        TextView tvPhone;
        TextView tvTotalPrice;
        ImageView ivDish;

        public ViewHolder(View itemView) {
            super(itemView);
            lvFoodname = (ListView) itemView.findViewById(R.id.lv_foodname);
            btnCall = (Button) itemView.findViewById(R.id.btn_call);
            btnArrive = (Button) itemView.findViewById(R.id.btn_arrive);
            tvPosition = (TextView) itemView.findViewById(R.id.tv_position);
            tvRemark = (TextView) itemView.findViewById(R.id.tv_remark);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvTotalPrice = (TextView) itemView.findViewById(R.id.tv_totalprice);
            ivDish = (ImageView) itemView.findViewById(R.id.iv_dish);
        }

        public void setAdapter(ArrayList<Dish> dishList) {
            String[] list = new String[dishList.size()];
            for (int i = 0; i < dishList.size(); i++) {
                Dish itemDish = dishList.get(i);
                StringBuilder builder = new StringBuilder();
                builder.append(itemDish.getDishName() + "*" + itemDish.getDishCount());
                list[i] = builder.toString();
            }
            ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, list);
            lvFoodname.setAdapter(adapter);
        }
    }

}
