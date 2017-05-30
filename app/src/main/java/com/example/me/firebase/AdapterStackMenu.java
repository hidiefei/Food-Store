package com.example.me.firebase;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.me.firebase.Store.Menu;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Me on 11/25/2016.
 */

public class AdapterStackMenu extends ArrayAdapter<Menu> {

    private List<Menu> items;
    private Context context;

    public AdapterStackMenu(Context context, int textViewResourceId,
                        List<Menu> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.itemstack, null);
        }
        Menu stackItem = items.get(position);
        if (stackItem != null) {
            // TextView định nghĩa trên stack_item.xml
            TextView textView = (TextView) itemView.findViewById(R.id.textView);
            // ImageView định nghĩa trên stack_item.xml
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


            if (textView != null) {
                textView.setText(stackItem.getGia());

                // "image1", "image2",..
                String imageName= stackItem.getLinkhinh();

                Picasso.with(context).setIndicatorsEnabled(true);

                Picasso.with(context)
                        .load(stackItem.getLinkhinh())
                        .into(imageView);
            }

        }
        return itemView;
    }


}
