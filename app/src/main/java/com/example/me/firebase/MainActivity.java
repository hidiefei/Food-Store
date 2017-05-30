package com.example.me.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.me.firebase.Login.LoginActivity;
import com.example.me.firebase.Store.AdapterStore;
import com.example.me.firebase.Store.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final String KEY_ID="key_id";

    ListView lv;
    DatabaseReference mData;
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseInstance;
    List<Menu> mMenu=null;

    ArrayList<Store> stores=new ArrayList<Store>();
    AdapterStore adapterStore=null;


    List<Store>aaa=new ArrayList<Store>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();

       /* UserProduct storeProduct=new UserProduct(1,11,"ndthong.uit");
         mData.child("ProductUser").push().setValue(storeProduct);*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);


        lv=(ListView)findViewById(R.id.lv);

        adapterStore=new AdapterStore(this,stores);
        lv.setAdapter(adapterStore);
       LoadData();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store storeposition=stores.get(position);
                Intent intent=new Intent(MainActivity.this,DetailStore.class);
                intent.putExtra("listStore",stores);
                intent.putExtra("storeposition",storeposition);
                intent.putExtra("id_product",String.valueOf(storeposition.getId()));
                Var.saveId(MainActivity.this,Var.KEY_ID,String.valueOf(storeposition.getId()));
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this,R.style.MyAlertDialogStyle);
                        adb.setTitle("Delete?");
                        adb.setIcon(R.drawable.delete);
                        adb.setMessage("Are you sure you want to delete " + stores.get(position).getTenStore());

                        adb.setNegativeButton("No", null);
                        adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String itemRemove=stores.get(position).getTenStore().toString();
                                Query itemQuery = mData.child("Store").orderByChild("tenStore").equalTo(itemRemove);

                                itemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                            Toast.makeText(MainActivity.this,"Xóa Thành Công!!!",Toast.LENGTH_LONG).show();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(MainActivity.this,"Xóa Không Thành Công!!!",Toast.LENGTH_LONG).show();
                                    }
                                });
                                stores.remove(position);
                                adapterStore.notifyDataSetChanged();
                            }});
                        adb.show();

                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Activity_AddStore.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    void LoadData(){
        mData.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store= new Store();
                store = dataSnapshot.getValue(Store.class);
                stores.add(store);
                adapterStore.notifyDataSetChanged();

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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.change_password) {
            Intent intent=new Intent(this,Change.class);
            startActivity(intent);
        } else if (id == R.id.Log_out) {
            auth.signOut();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
