package com.example.me.firebase;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me.firebase.Store.Menu;
import com.example.me.firebase.Store.Store;
import com.example.me.firebase.Store.UserProduct;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    Store storeposition;
    private List<Menu> mMenus;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    DatabaseReference mData;
    String sl;
    int a ;
    String ab="";
    public MenuAdapter(Context context, List<Menu> datas,Store store) {
        mContext = context;
        mMenus = datas;
        mLayoutInflater = LayoutInflater.from(context);
        storeposition=store;
    }


    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.itemmenu, parent, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.MenuViewHolder holder, final int position) {
        final Menu menu=mMenus.get(position);
        int a=storeposition.getMenuList().get(position).getId_menu();
        ab="";
        ab=String.valueOf(position);
        holder.tvtitle.setText(menu.getTenmon().toString());
        holder.tvprice.setText(String.valueOf(menu.getGia()));
        Picasso.with(mContext).setIndicatorsEnabled(true);

        Picasso.with(mContext)
                .load(menu.getLinkhinh())
                .into(holder.imgmenu);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData = FirebaseDatabase.getInstance().getReference();
                final Dialog dialog=new Dialog(mContext);
                dialog.setContentView(R.layout.dialog);
                final EditText edtsl= (EditText) dialog.findViewById(R.id.edtsoluong);
                Button btnsl= (Button) dialog.findViewById(R.id.btnsoluong);
                dialog.show();
                btnsl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sl="";
                        sl=edtsl.getText().toString();
                        dialog.dismiss();
                        String id_menu=String.valueOf(menu.getId_menu());

                        UserProduct user=new UserProduct(Var.getEmail(mContext),Var.getid(mContext),ab,sl);
                        mData.child("ProductUser").child(Var.getEmail(mContext)).push().setValue(user);
                        Toast.makeText(mContext,"Đã thêm vào giỏ hàng thành công!!!",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }

    public void customdialog(){
        final Dialog dialog=new Dialog(mContext);
        dialog.setContentView(R.layout.dialog);
        final EditText edtsl= (EditText) dialog.findViewById(R.id.edtsoluong);
        Button btnsl= (Button) dialog.findViewById(R.id.btnsoluong);
        btnsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl="";
                sl=edtsl.getText().toString();
                dialog.dismiss();

            }
        });
    }
    @Override
    public int getItemCount() {
        return mMenus.size();
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgmenu;
        TextView tvtitle,tvprice;
        public MenuViewHolder(View itemView) {
            super(itemView);
            imgmenu= (ImageView) itemView.findViewById(R.id.imgmenu);
            tvtitle= (TextView) itemView.findViewById(R.id.tvtitle);
            tvprice= (TextView) itemView.findViewById(R.id.tvprice);
        }
    }
}

