package com.example.me.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me.firebase.Store.Menu;
import com.example.me.firebase.Store.Store;
import com.example.me.firebase.Store.UserProduct;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AdapterOrder extends BaseAdapter {
    List<Store>storeOld;
    DatabaseReference mData;
    private List<UserProduct> mStore;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    UserProduct user;
    Store store=new Store();
    ArrayList<Store> listStore=new ArrayList<Store>();
    Store tem=new Store();

    List<Store>getStoreMain=new ArrayList<Store>();

    List<Menu>menus=new ArrayList<Menu>();

    public AdapterOrder(Context context, List<UserProduct> stores,List<Store> storeOld,UserProduct user) {
        mData = FirebaseDatabase.getInstance().getReference();
        this.user=user;
        this.getStoreMain=storeOld;
        this.mContext = context;
        this.mStore = stores;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mStore != null ? mStore.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mStore.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_store, null);
            holder = new ViewHolder();
            holder.linkHinh = (ImageView) convertView.findViewById(R.id.imgHinhstore);
            holder.ten = (TextView) convertView.findViewById(R.id.tvNameStore);
            holder.gia = (TextView) convertView.findViewById(R.id.tvGia);
            holder.soluong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserProduct userProduct=mStore.get(position);

        Store st=new Store();
        st=getStoreMain.get(Integer.parseInt(userProduct.getId()));
        menus=getStoreMain.get(Integer.parseInt(userProduct.getId().toString())).getMenuList();
        String id_menu=userProduct.getId_menu().toString();
        holder.ten.setText(menus.get(Integer.parseInt(id_menu)).getTenmon().toString());
        holder.gia.setText("Giá:"+menus.get(Integer.parseInt(id_menu)).getGia().toString());
        holder.soluong.setText("Số Lượng:"+userProduct.getSoluong());
        Picasso.with(mContext).setIndicatorsEnabled(true);

        Picasso.with(mContext)
                .load(menus.get(Integer.parseInt(id_menu)).getLinkhinh().toString())
                .into(holder.linkHinh);
        return convertView;
    }
    public Store getStore(final int position) {
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                store = dataSnapshot.getValue(Store.class);
                listStore.add(store);
                if (position<listStore.size()){
                    tem = listStore.get(position);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return  tem;
    }

    public String getGia(Store store,String id_menu){
        String gia="";
        List<Menu> menu=new ArrayList<Menu>();
        for (Menu a : menu){
            if (String.valueOf(a.getId_menu()).equals(id_menu)){
                gia=a.getGia().toString();
            }
        }
        return gia;
    }

    public class ViewHolder {
        private ImageView linkHinh;
        private TextView ten;
        private TextView gia;
        private TextView soluong;
    }

}
