package com.example.me.firebase.Store;

/**
 * Created by THONG on 5/27/2017.
 */

public class UserProduct {
    String  user,id,id_menu,soluong;

    public UserProduct() {

    }

    public UserProduct(String user, String id, String id_menu, String soluong) {
        this.user = user;
        this.id = id;
        this.id_menu = id_menu;
        this.soluong = soluong;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }
}
