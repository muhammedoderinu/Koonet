package com.hfad.koonect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.hfad.koonect.login.LogInPage;
import com.hfad.koonect.signup.SignUpPage;

public class MainActivity extends AppCompatActivity {
    TextView signUpLinkButton;
    Button loginButton;
    String userEmail;
    String userPassword;
    TextInputEditText userEmailRaw;
    TextInputEditText userPasswordRaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpLinkButton = findViewById(R.id.signUplink);
        loginButton = findViewById(R.id.logInButton);
        userEmailRaw = findViewById(R.id.emailInput);
        userPasswordRaw = findViewById(R.id.passwordInput);



        signUpLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInPage logInPage = new LogInPage();
                logInPage.linkSignUpPage(getBaseContext());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword = userPasswordRaw.getText().toString();
                userEmail =  userEmailRaw.getText().toString();
                LogInPage logInPage = new LogInPage();
                logInPage.validateUser(MainActivity.this,getBaseContext(),
                                      userEmail,userPassword);
                ;

            }
        });





    }

    @Override
    public void onStart(){
        super.onStart();
        LogInPage logInPage = new LogInPage();
        if(logInPage.isUserSignedIn()) {
            logInPage.loginUser(getBaseContext());
        }
    }
}