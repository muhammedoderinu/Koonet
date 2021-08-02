package com.hfad.koonect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hfad.koonect.login.LogInPage;

public class MainActivity extends AppCompatActivity {
    TextView signUpLinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpLinkButton = findViewById(R.id.signUplink);

        signUpLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInPage logInPage = new LogInPage();
                logInPage.linkSignUpPage(getBaseContext());
            }
        });


    }
}