package com.hfad.koonect.options;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.koonect.SellerProfilePage;
import com.hfad.koonect.entity.Seller;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class SellerProfile {
    Listener listener;

    public void openSellerProfilePage(Context context){
        Intent intent = new Intent(context, SellerProfilePage.class);
        context.startActivity(intent);
    }

    public void downloadSellerProfile(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
       FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference mPostReference = FirebaseDatabase.getInstance()
                .getReference(user.getUid()+"/Seller Details");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Seller seller = new Seller();
                Seller sellers = dataSnapshot.getValue(seller.getClass());
                listener.setSellerInfo(sellers);
                downloadImageFromCloud();


                // ..
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);

    }

    public interface Listener{
        void setSellerInfo(Seller sellerInfo);
        void downloadProfilePics(Uri uri);
    }

    public void setListener(Listener listener){
        this.listener = listener;

    }

    public void selectImage(Context context, ActivityResultLauncher<Intent> launcher,
                            ActivityResultLauncher<Intent> launcher2, Activity activity) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    launcher.launch(takePicture);


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    launcher2.launch(pickPhoto);



                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void downloadImageFromCloud(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference islandRef = storage.getReference().child(user.getUid()+"/Seller ProfilePic");

        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                listener.downloadProfilePics(uri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {

            }
        });

    }




}
