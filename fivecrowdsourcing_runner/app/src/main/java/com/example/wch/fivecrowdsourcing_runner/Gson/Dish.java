package com.example.wch.fivecrowdsourcing_runner.Gson;
/**
 * Created by zc on 2017/7/31.
 */

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 菜品
 */
public class Dish extends DataSupport implements Serializable {
    private String dishName;
    private int dishCount;

    public Dish(String dishName, int dishCount) {
        this.dishName = dishName;
        this.dishCount = dishCount;
    }

    public void setDishCount(int dishCount) {
        this.dishCount = dishCount;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishCount() {
        return dishCount;
    }

    public String getDishName() {
        return dishName;
    }

}