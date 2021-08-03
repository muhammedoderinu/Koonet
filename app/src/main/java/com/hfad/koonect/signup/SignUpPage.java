package com.hfad.koonect.signup;

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
import com.hfad.koonect.database.SellerDatabase;

import static android.content.ContentValues.TAG;

public class SignUpPage {
    private FirebaseAuth mAuth;

    public void signUpUser(Activity activity, Context context,
                           String userEmail, String userPassword,
                           String phoneNumber, String userName){

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SellerDatabase sellerDatabase = new SellerDatabase();
                            sellerDatabase.storeUserDetails(userEmail,phoneNumber,userName);
                            signInUser(context);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public Boolean isUserSignIn(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            return true;
        }
        return false;
    }

    public void linkLogInPage(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    public void signInUser(Context context){
        Intent intent = new Intent(context, SellerHomePage.class);
        context.startActivity(intent);

    }
}
