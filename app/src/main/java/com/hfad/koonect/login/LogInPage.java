package com.hfad.koonect.login;

import android.content.Context;
import android.content.Intent;

import com.hfad.koonect.SignUP;

public class LogInPage {

    public void loginUser(Context context){

    }
    public void linkSignUpPage(Context context){
        Intent intent = new Intent(context, SignUP.class);
        context.startActivity(intent);

    }
}
