package com.hfad.koonect.signup;

import android.content.Context;
import android.content.Intent;

import com.hfad.koonect.MainActivity;
import com.hfad.koonect.SellerHomePage;

public class SignUpPage {
    public void signUpUser(Context context){
        Intent intent = new Intent(context, SellerHomePage.class);
        context.startActivity(intent);

    }

    public void linkLogInPage(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
