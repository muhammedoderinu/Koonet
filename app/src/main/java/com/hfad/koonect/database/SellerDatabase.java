package com.hfad.koonect.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hfad.koonect.entity.Product;
import com.hfad.koonect.entity.Seller;

import java.util.ArrayList;

public class SellerDatabase {
    private FirebaseAuth mAuth;
    String downloadPath;
    Product product;
    Listener listener;
    ArrayList<String> downloadPaths = new ArrayList<String>();



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

    public  void  uploadThumbnailsToCloud(ArrayList<byte[]> bytesImage,ArrayList<String> picNames,
                                          String productName,String productCat, String productPrice,
                                         boolean isVideo, boolean isOld, boolean isNew ){

        FirebaseAuth auth= FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        for(int i=0;i<bytesImage.size();i++) {
            StorageReference storageRef = storage.getReference().child(user.getUid() +
                    "/Picture_Thumbnails/" + picNames.get(i));
            UploadTask uploadTask = storageRef.putBytes(bytesImage.get(i));

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public synchronized Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public synchronized void onComplete(Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        downloadPath = downloadUri.toString();
                        downloadPaths.add(downloadPath);
                        listener.onDataGot(downloadPaths);




                    } else {
                        // Handle failures
                        // ...
                    }

                }
            });

        }
         setListener(new Listener() {
             @Override
             public void onDataGot(ArrayList<String> downloadPaths) {
                 if(downloadPaths.size()==4) {
                     uploadProductInfo(productName, productCat, productPrice,
                             downloadPaths, isVideo, isOld,isNew);
                 }

             }
         });

    }

    public void uploadProductInfo(String productName, String ProductCat, String productPrice,
                                  ArrayList<String> downloadPaths, boolean isVideo, boolean isOld, boolean isNew){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String Id = user.getUid();
        String thumbnail1 = downloadPaths.get(0);
        String thumbnail2 = downloadPaths.get(1);
        String thumbnail3 = downloadPaths.get(2);
        String thumbnail4 = downloadPaths.get(3);

         Product product = new Product(productName,ProductCat,productPrice,Id,
                thumbnail1,thumbnail2,thumbnail3,thumbnail4, isVideo, isOld,isNew);
        Log.d("memme", "storeUserDetails: ");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(Id+"/Product Details").setValue(product);


    }

    public interface Listener{

        void onDataGot(ArrayList<String> downloadPaths);

    }

    public void setListener(Listener listener){
        this.listener = listener;
    }








}
