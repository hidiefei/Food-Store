package com.example.me.firebase.Store;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Me on 9/29/2016.
 */

public class AdapterStore extends BaseAdapter {
    //thong

    private ArrayList<Store> mStore;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public AdapterStore(Context context, ArrayList<Store> stores) {
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
            convertView = mLayoutInflater.inflate(R.layout.itemliststore, null);
            holder = new ViewHolder();
            holder.linkHinh = (ImageView) convertView.findViewById(R.id.imghinhstore);
            holder.ten = (TextView) convertView.findViewById(R.id.txtNameStore);
            holder.time = (TextView) convertView.findViewById(R.id.txttime);
            holder.diachi = (TextView) convertView.findViewById(R.id.txtdiachi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Store store = (Store) this.mStore.get(position);
        //Picasso.with(mContext).load(mStore.get(position).getLinkHinh().toString()).into(holder.linkHinh);
        Picasso.with(mContext).setIndicatorsEnabled(true);

        Picasso.with(mContext)
                .load(mStore.get(position).getLinkHinh().toString())
                .into(holder.linkHinh);
        holder.ten.setText(store.getTenStore().toString());
        holder.time.setText(store.getTime().toString());
        holder.diachi.setText(store.getDiachi().toString());
        Log.d("aafa",mStore.get(position).getSdt().toString());
        return convertView;
    }

    public class ViewHolder {
        private ImageView linkHinh;
        private TextView ten;
        private TextView time;
        private TextView diachi;
    }

}
