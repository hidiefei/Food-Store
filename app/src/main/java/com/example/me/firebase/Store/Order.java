package com.example.me.firebase.Store;

import java.util.List;

/**
 * Created by THONG on 5/27/2017.
 */

public class Order {
    List<UserProduct> list;

    public List<UserProduct> getList() {
        return list;
    }

    public void setList(List<UserProduct> list) {
        this.list = list;
    }
    public Order() {

    }

    public Order(List<UserProduct> list) {

        this.list = list;
    }
}
