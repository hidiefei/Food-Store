package com.example.me.firebase.Store;

import java.io.Serializable;

/**
 * Created by Me on 9/28/2016.
 */

public class Menu implements Serializable{
    String linkhinh,tenmon;
    String gia;
    int id_menu;

    public Menu() {

    }

    public Menu(String linkhinh, String tenmon, String gia) {
        this.linkhinh = linkhinh;
        this.tenmon = tenmon;
        this.gia = gia;
    }

    public Menu(String linkhinh, String tenmon, String gia, int id_menu) {
        this.linkhinh = linkhinh;
        this.tenmon = tenmon;
        this.gia = gia;
        this.id_menu = id_menu;
    }

    public String getLinkhinh() {
        return linkhinh;
    }

    public void setLinkhinh(String linkhinh) {
        this.linkhinh = linkhinh;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }
}
