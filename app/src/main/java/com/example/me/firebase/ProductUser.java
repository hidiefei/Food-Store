package com.example.me.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.me.firebase.Store.Store;
import com.example.me.firebase.Store.UserProduct;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductUser extends AppCompatActivity {

    DatabaseReference mData;
    ListView lvStore;
    AdapterOrder adapterOrder;
    List<UserProduct>listData=new ArrayList<UserProduct>();
    List<Store> listStore=new ArrayList<Store>();
    List<Store>getStoreMain=new ArrayList<Store>();
    UserProduct user=new UserProduct();
    ImageView imgback;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_user);
        lvStore= (ListView) findViewById(R.id.lvUserProduct);

        toolbar = (Toolbar) findViewById(R.id.toolbarOrderback);
        title = (TextView) toolbar.findViewById(R.id.titletoolbarorder);
        title.setText("Danh sách món đã đặt");
        imgback = (ImageView) toolbar.findViewById(R.id.imgOrderback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        getStoreMain= (List<Store>) intent.getSerializableExtra("listStore");
        user= (UserProduct) intent.getSerializableExtra("user");
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("ProductUser").child(Var.getEmail(this)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserProduct store= new UserProduct();
                store = dataSnapshot.getValue(UserProduct.class);
                listData.add(store);
                adapterOrder.notifyDataSetChanged();
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
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store a= new Store();
                a=dataSnapshot.getValue(Store.class);
                listStore.add(a);
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

        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        adapterOrder=new AdapterOrder(this,listData,getStoreMain,user);
        lvStore.setAdapter(adapterOrder);
       // getDataByEmail(stores);
    }
   /* public void getDataByEmail(List<UserProduct> temp){
        for (UserProduct a:temp) {
            if (a.getUser().toString().equals(Var.getEmail(ProductUser.this))) {
                listStores.add(a);
                list.add(a.getId_menu());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }*/
}
