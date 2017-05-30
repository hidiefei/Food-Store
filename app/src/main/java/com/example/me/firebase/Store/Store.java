package com.example.me.firebase.Store;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 9/28/2016.
 */

public class Store implements Serializable {
    String linkHinh;
    String tenStore;
    String time;
    String diachi;
    String sdt;
    List<Menu> menuList;
    int id;

    public Store(String linkHinh, String tenStore, String time, String diachi, String sdt, List<Menu> menuList) {
        this.linkHinh = linkHinh;
        this.tenStore = tenStore;
        this.time = time;
        this.diachi = diachi;
        this.sdt = sdt;
        this.menuList = menuList;
    }

    public Store() {
    }

    public Store(String linkHinh, String tenStore, String time, String diachi, String sdt, List<Menu> menuList, int id) {
        this.linkHinh = linkHinh;
        this.tenStore = tenStore;
        this.time = time;
        this.diachi = diachi;
        this.sdt = sdt;
        this.menuList = menuList;
        this.id = id;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }

    public String getTenStore() {
        return tenStore;
    }

    public void setTenStore(String tenStore) {
        this.tenStore = tenStore;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
