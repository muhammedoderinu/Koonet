package com.hfad.koonect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.koonect.database.SellerDatabase;
import com.hfad.koonect.entity.Seller;
import com.hfad.koonect.options.SellerProfile;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class SellerProfilePage extends AppCompatActivity {

    EditText userNameRaw;
    EditText emailRaw;
    EditText phoneNumberRaw;
    SellerProfile sellerProfile;
    String userName;
    String email;
    String phoneNumber;
    CircleImageView profilePics;
    ImageButton profilePicsEdit;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    ActivityResultLauncher<Intent> someActivityResultTwoLauncher;
    TextView userNameEditRaw, savePicture;
    SwipeRefreshLayout refreshLayout;
    Button saveProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userNameRaw = findViewById(R.id.userNameProfile);
        emailRaw = findViewById(R.id.sellerEmailProfile);
        phoneNumberRaw = findViewById(R.id.sellerPhoneNumberProfile);
        profilePics = findViewById(R.id.sellerProfilePicss);
        profilePicsEdit = findViewById(R.id.profilePicsEdit);
        userNameEditRaw  = findViewById(R.id.userNameEdit);
        refreshLayout  =findViewById(R.id.refreshLayout);
        saveProfile = findViewById(R.id.saveProfile);
        savePicture = findViewById(R.id.savePicture);

        saveProfile.setEnabled(false);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                sellerProfile = new SellerProfile();
                sellerProfile.downloadSellerProfile();
                sellerProfile.setListener(new SellerProfile.Listener() {
                    @Override
                    public void setSellerInfo(Seller sellerInfo) { enableProfile(true);
                        userName =  sellerInfo.getUserName();
                        email = sellerInfo.getEmail();
                        phoneNumber = sellerInfo.getPhoneNumber();
                        userNameRaw.setText(userName);
                        emailRaw.setText(email);
                        phoneNumberRaw.setText(phoneNumber);
                        enableProfile(false);


                    }

                    @Override
                    public void downloadProfilePics(Uri uri) {
                        Picasso.get()
                                .load(uri)
                                .into(profilePics);
                        saveProfile.setEnabled(true);
                    }

                });
                refreshLayout.setRefreshing(false);


            }
        });

        userNameEditRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableProfile(true);
            }
        });

        enableProfile(false);
        sellerProfile = new SellerProfile();
        sellerProfile.downloadSellerProfile();
        sellerProfile.setListener(new SellerProfile.Listener() {
            @Override
            public void setSellerInfo(Seller sellerInfo) {
                enableProfile(true);
               userName =  sellerInfo.getUserName();
               email = sellerInfo.getEmail();
               phoneNumber = sellerInfo.getPhoneNumber();
               userNameRaw.setText(userName);
               emailRaw.setText(email);
               phoneNumberRaw.setText(phoneNumber);
               enableProfile(false);


            }

            @Override
            public void downloadProfilePics(Uri uri) {
                Picasso.get()
                        .load(uri)
                        .into(profilePics);
                saveProfile.setEnabled(true);


            }

        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String userName=userNameRaw.getText().toString();
               String email =  emailRaw.getText().toString();
               String phoneNumber =  phoneNumberRaw.getText().toString();
                SellerDatabase sellerDatabase = new SellerDatabase();
                sellerDatabase.storeUserDetails(email,phoneNumber,userName);

            }
        });

        savePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(profilePics.getDrawable()==null) {
                SellerDatabase sellerDatabase = new SellerDatabase();
                byte[] data = getProfilePics();
                sellerDatabase.uploadProfilePicsToCloud(data);
                Toast.makeText(SellerProfilePage.this,"i am empty", Toast.LENGTH_LONG).show();


            }

            }
        });


        profilePicsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellerProfile sellerProfile = new SellerProfile();
               sellerProfile.selectImage(SellerProfilePage.this,someActivityResultTwoLauncher,
                       someActivityResultLauncher,
                       SellerProfilePage.this);

            }
        });
        onActivityResult();
        onActivityResultTwo();



    }

    public void enableProfile(Boolean check){
        userNameRaw.setEnabled(check);
        emailRaw.setEnabled(check);
        phoneNumberRaw.setEnabled(check);

    }

    public void onResume(){
        super.onResume();

    }

    public byte[] getProfilePics(){
        profilePics.setDrawingCacheEnabled(true);
        profilePics.buildDrawingCache();
       Bitmap bitmap =  ((BitmapDrawable)profilePics.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }



    public void onActivityResult(){
        Log.d("ok i am called", "onActivityResult: ");

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("i am called", "onActivityResult: ");

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            if (data.getData() == null) {
                                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                                profilePics.setImageBitmap(bitmap);

                            } else {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap
                                            (SellerProfilePage.this.getContentResolver(), data.getData());
                                    profilePics.setImageBitmap(bitmap);

                                } catch (Exception e) {

                                }


                            }
                            //doSomeOperations();
                        }
                    }
                });


    }

    public void onActivityResultTwo(){


        someActivityResultTwoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        Picasso.get()
                                .load(picturePath)
                                .into(profilePics);


                    }
                });



    }





}