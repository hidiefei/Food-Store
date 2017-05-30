package com.example.me.firebase;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.me.firebase.Store.Menu;
import com.example.me.firebase.Store.Store;
import com.example.me.firebase.Store.UserProduct;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.LikeView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailStore extends AppCompatActivity {
    //thong
    ListView lv;
    ImageView img, imgmap, imgcall;
    TextView txtTen, txtDiaChi, txtThoiGian, txtSDT;
    RecyclerView rv;
    List<Menu> mMenus = new ArrayList<Menu>();
    ArrayAdapter adapter = null;
    MenuAdapter menuAdapter ;
    AdapterStackMenu adapterStackMenu;
    List<String> lit = new ArrayList<String>();
    private LinearLayoutManager mLinearLayoutManager;
    ImageView imgstore;
    Toolbar toolbar;
    TextView title;
    ImageView imgdirect;

    EditText edtsl;
    Button btnsl;
    String sl="";
    Dialog dialog = null;
    List<Store> getStoreMain=new ArrayList<Store>();

    DatabaseReference mData;
    List<UserProduct>userProductList=new ArrayList<UserProduct>();
    UserProduct user=new UserProduct();
    List<UserProduct>products=new ArrayList<UserProduct>();

    //thong
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mData = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_detail_store);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Chi Tiết Cửa Hàng");
        imgdirect= (ImageView) toolbar.findViewById(R.id.imgdirect);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AnhXa();
        //LikeView
        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.BOX_COUNT);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType(
                "https://www.foody.vn/", LikeView.ObjectType.OPEN_GRAPH);

        Intent intent = getIntent();
        final Store storeposition = (Store) intent.getSerializableExtra("storeposition");
        getStoreMain= (List<Store>) intent.getSerializableExtra("listStore");
        final String diachi = storeposition.getDiachi().toString();
        txtTen.setText(storeposition.getTenStore().toString());
        txtThoiGian.setText(storeposition.getTime().toString());
        txtDiaChi.setText(storeposition.getDiachi().toString());
        txtSDT.setText(storeposition.getSdt().toString());

        imgmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(DetailStore.this, MapsActivity.class);
                    i.putExtra("DiaChi", diachi);
                    startActivity(i);
                } catch (Exception e) {
                    Log.v("Failed", e.getMessage());
                }
            }
        });
        mData.child("ProductUser").child(Var.getEmail(DetailStore.this)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserProduct userProduct=new UserProduct();
                userProduct=dataSnapshot.getValue(UserProduct.class);
                products.add(userProduct);
                /*UserProduct userProduct=new UserProduct();
                userProduct=dataSnapshot.getValue(UserProduct.class);
                userProductList.add(userProduct);
                for (UserProduct product : userProductList){
                    if (product.getUser().equals(Var.getEmail(DetailStore.this))){
                        user=product;
                    }
                }*/
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

        imgdirect.setImageResource(R.drawable.shopping_cart);
        imgdirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(DetailStore.this,ProductUser.class);
                it.putExtra("listStore", (Serializable) getStoreMain);
                it.putExtra("storeposition",storeposition);
                //      it.putExtra("products", (Serializable) products);
                startActivity(it);

            }
        });


        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallClicked(storeposition);
            }
        });
        FloatingActionButton fabbun = (FloatingActionButton) findViewById(R.id.fabdetail);
        fabbun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mMenus = storeposition.getMenuList();

        imgstore.setVisibility(View.GONE);
        menuAdapter = new MenuAdapter(DetailStore.this, mMenus,storeposition);
        rv.setAdapter(menuAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        //}
    }
    public String getslFromDialog(){
        showDialog(1);
        return sl;
    }
    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case 1:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                edtsl= (EditText) dialog.findViewById(R.id.edtsoluong);
                btnsl= (Button) dialog.findViewById(R.id.btnsoluong);
                btnsl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sl="";
                        sl=edtsl.getText().toString();
                        removeDialog(1);
                    }
                });
                break;
        }
        return dialog;
    }
    //tien
    public void onCallClicked(Store store) {
        Uri uri = Uri.parse("tel:" + store.getSdt());
        Intent intent = new Intent(Intent.ACTION_DIAL).setData(uri);
        startActivity(intent);
    }
    private void AnhXa() {
        imgstore= (ImageView) findViewById(R.id.imgstore);
        rv = (RecyclerView) findViewById(R.id.rv);
        lv = (ListView) findViewById(R.id.lv);
        txtTen = (TextView) findViewById(R.id.txtDetailStore);
        txtDiaChi = (TextView) findViewById(R.id.txtDetailAddress);
        txtThoiGian = (TextView) findViewById(R.id.txtDetailOpen);
        txtSDT = (TextView) findViewById(R.id.txtDetailPhone);
        imgmap = (ImageView) findViewById(R.id.imgmap);
        imgcall = (ImageView) findViewById(R.id.imgcall);

    }
}
