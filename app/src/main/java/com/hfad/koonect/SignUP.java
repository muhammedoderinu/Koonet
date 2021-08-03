package com.hfad.koonect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.hfad.koonect.signup.SignUpPage;

import java.util.Objects;

public class SignUP extends AppCompatActivity {
    Button signUpButton;
    TextView loginLink;
    TextInputEditText userEmailRaw;
    TextInputEditText userPasswordRaw;
    TextInputEditText userNameRaw;
    TextInputEditText userPhoneNumberRaw;
    String userEmail;
    String userPassword;
    String userName;
    String userPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpButton = findViewById(R.id.signUpButton);
        loginLink = findViewById(R.id.loginLink);
        userEmailRaw = findViewById(R.id.signUpEmail);
        userPasswordRaw = findViewById(R.id.signUpPassword);
        userNameRaw = findViewById(R.id.signUpUserName);
        userPhoneNumberRaw = findViewById(R.id.signUpPhoneNumber);





        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpPage signUpPage = new SignUpPage();
                signUpPage.linkLogInPage(getBaseContext());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = Objects.requireNonNull(userEmailRaw.getText()).toString();

                String userPassword = userPasswordRaw.getText().toString();
                String userName = userNameRaw.getText().toString();
                String userPhoneNumber = userPhoneNumberRaw.getText().toString();
                SignUpPage signUpPage = new SignUpPage();
                signUpPage.signUpUser(SignUP.this, getBaseContext()
                        ,userEmail, userPassword,userPhoneNumber,userName);

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        SignUpPage signUpPage = new SignUpPage();
        if(signUpPage.isUserSignIn()){
            signUpPage.signInUser(getBaseContext());
        }
    }
}