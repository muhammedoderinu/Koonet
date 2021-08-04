package com.hfad.koonect.options;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.hfad.koonect.MainActivity;
import com.hfad.koonect.login.LogInPage;

public class LogOut {

    public void logSellerOut(Context context){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
