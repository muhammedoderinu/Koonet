package com.hfad.koonect.database;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.koonect.entity.Seller;

public class SellerDatabase {
    private FirebaseAuth mAuth;

    public void storeUserDetails(String email, String phoneNumber, String userName){
        Seller seller = new Seller(email,phoneNumber,userName);
        Log.d("memme", "storeUserDetails: ");
        mAuth = FirebaseAuth.getInstance();
       FirebaseUser user =  mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child(user.getUid()+"/Seller Details").setValue(seller);



    }
}
