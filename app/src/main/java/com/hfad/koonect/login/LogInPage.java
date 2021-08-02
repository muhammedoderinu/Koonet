package com.hfad.koonect.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hfad.koonect.MainActivity;
import com.hfad.koonect.SellerHomePage;
import com.hfad.koonect.SignUP;

import static android.content.ContentValues.TAG;

public class LogInPage {
    FirebaseAuth mAuth;

    public void loginUser(Context context){
        Intent intent = new Intent(context, SellerHomePage.class);
        context.startActivity(intent);


    }


    public void linkSignUpPage(Context context){
        Intent intent = new Intent(context, SignUP.class);
        context.startActivity(intent);

    }

    public void validateUser(Activity activity,Context context, String userEmail, String userPassword){
        mAuth = FirebaseAuth.getInstance();
        if(!userEmail.isEmpty()&&!userPassword.isEmpty())
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loginUser(context);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure");
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public Boolean isUserSignedIn(){

        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            return true;

        }
        return false;
    }
}
