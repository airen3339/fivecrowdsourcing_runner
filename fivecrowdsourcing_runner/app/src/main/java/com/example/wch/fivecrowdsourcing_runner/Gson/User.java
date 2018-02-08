package com.example.wch.fivecrowdsourcing_runner.Gson;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by zc on 2017/7/31.
 */

public class User extends DataSupport implements Serializable {
    private String position;//位置
    private String phone;//电话
    private String name;//姓名

    public User(String name, String position, String phone) {
        this.phone = phone;
        this.position = position;
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {

        return position;
    }
}