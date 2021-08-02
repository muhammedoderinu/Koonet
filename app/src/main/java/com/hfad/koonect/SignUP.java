package com.hfad.koonect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hfad.koonect.signup.SignUpPage;

public class SignUP extends AppCompatActivity {
    Button signUpButton;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpButton = findViewById(R.id.signUpButton);
        loginLink = findViewById(R.id.loginLink);

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
                SignUpPage signUpPage = new SignUpPage();
                signUpPage.signUpUser(getBaseContext());
            }
        });
    }
}