package com.hfad.koonect.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hfad.koonect.entity.Product;
import com.hfad.koonect.entity.Seller;

public class SellerDatabase {
    private FirebaseAuth mAuth;

    public void storeUserDetails(String email, String phoneNumber, String userName){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String Id = user.getUid();
        Seller seller = new Seller(email,phoneNumber,userName, Id);
        Log.d("memme", "storeUserDetails: ");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(Id+"/Seller Details").setValue(seller);



    }

    public void uploadProfilePicsToCloud(byte[] data){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(user.getUid()+"/Seller ProfilePic");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });


    }

    public void uploadThumbnailsToCloud(byte[] data,String picName){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(user.getUid()+"/Picture_Thumbnails/"+picName);
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

    }

    public void uploadProductInfo(String productName,String ProductCat){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String Id = user.getUid();
        Product product = new Product(productName,ProductCat);
        Log.d("memme", "storeUserDetails: ");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(Id+"/Product Details").setValue(product);



    }



}
