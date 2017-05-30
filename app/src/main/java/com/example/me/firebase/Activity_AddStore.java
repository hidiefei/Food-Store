package com.example.me.firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.me.firebase.Store.Menu;
import com.example.me.firebase.Store.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_AddStore extends AppCompatActivity {
    ImageView imgHinh;
    EditText edtTen, edtThoiGian, edtDiaChi,edtSDT;
    Button btncancel, btnsave;
    private static final int PICK_IMAGE = 1;
    String stringAvatar = null;


    ArrayList<Menu> menu=new ArrayList<Menu>();
    List<Menu> menuList=new ArrayList<Menu>();
    List<Menu> menuList1=new ArrayList<Menu>();
    List<Menu> menuList2=new ArrayList<Menu>();
    List<Menu> menuList3=new ArrayList<Menu>();
    List<Menu> menuList4=new ArrayList<Menu>();
    List<Menu> menuList5=new ArrayList<Menu>();
    List<Menu> menuList6=new ArrayList<Menu>();


    DatabaseReference mData;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_store);
        mData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://demohbb.appspot.com");
        Anhxa();

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
/*        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });*/
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                /* Store store=new Store("https://media.foody.vn/res/g1/5619/prof/s480x300/foody-mobile-waffle-place-mb-jpg-921-635751645633749235.jpg",
                        "Waffle Place - Bánh Tổ Ong Nướng",
                        "00:00 - 21:30",
                        "120F Đinh Tiên Hoàng,  Quận 1, TP. HCM",
                        "0909506006");
                finish();


                Menu menu1=new Menu("https://media.foody.vn/res/g1/5619/s570x570/foody-1-635889689768842779.png"
                        ,"Bánh waffle phủ đường bột","35.000đ");
                Menu menu2=new Menu("https://media.foody.vn/res/g1/5619/s570x570/foody-2-635889689881318976.png"
                        ,"Bánh waffle với chuối tươi","50.000đ");
                Menu menu3=new Menu("https://media.foody.vn/res/g1/5619/s570x570/foody-4-635889690074135315.png"
                        ,"Bánh waffle phủ siro","40.000đ");
                Menu menu4=new Menu("https://media.foody.vn/res/g1/5619/s570x570/foody-7-635889690384887861.png"
                        ,"Bánh waffle chà bông","50.000đ");
                menuList.add(menu1);
                menuList.add(menu2);
                menuList.add(menu3);
                menuList.add(menu4);
                store.setMenuList(menuList);
                mData.child("Store").push().setValue(store); */

            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child(("image" + calendar.getTimeInMillis() + ".jpg"));
                imgHinh.setDrawingCacheEnabled(true);
                imgHinh.buildDrawingCache();
                Bitmap bitmap = imgHinh.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(Activity_AddStore.this, "Lỗi!!!!", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(Activity_AddStore.this, "Thành Công", Toast.LENGTH_LONG).show();
                        Log.d("AAA", downloadUrl + "");

                        Store data = new Store(String.valueOf(downloadUrl), edtTen.getText().toString(), edtThoiGian.getText().toString(),
                                edtDiaChi.getText().toString(),edtSDT.getText().toString(),menu);
                        mData.child("Store").push().setValue(data);
                        finish();
                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        Bitmap pick = null;
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    stringAvatar = BitMapToString(bitmap);
                    imgHinh.setImageBitmap(bitmap);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        pick = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringAvatar = BitMapToString(pick);
                    imgHinh.setImageBitmap(pick);
                }
                break;
        }
    }

    public void Anhxa() {
        imgHinh = (ImageView) findViewById(R.id.imagehinh);
        edtTen = (EditText) findViewById(R.id.edtten);
        edtThoiGian = (EditText) findViewById(R.id.edttime);
        edtDiaChi = (EditText) findViewById(R.id.edtdiachi);
        edtSDT=(EditText) findViewById(R.id.edtsdt);
        btncancel = (Button) findViewById(R.id.btncancel);
        btnsave = (Button) findViewById(R.id.btnsave);
    }

    private void selectImage() {
        final CharSequence[] options = {"Máy ảnh", "Thư viện ảnh", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AddStore.this);
        builder.setTitle("Lựa chọn");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Máy ảnh")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Thư viện ảnh")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
