package com.example.wch.fivecrowdsourcing_runner.Gson;

/**
 * Created by zc on 2017/8/3.
 */

public class Me {
    String name;
    String account;
    String password;
    String token;
    String phone;
    int count;


    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public int getCount() {
        return count;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }
}
