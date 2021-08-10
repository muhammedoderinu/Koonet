package com.hfad.koonect.sellerhomepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hfad.koonect.R;

public class HomePage {

    public void selectThumbnails(Context context, ActivityResultLauncher<Intent> launcher,
                                 ActivityResultLauncher<Intent> launcher2, Activity activity){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    launcher.launch(takePicture);


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    launcher2.launch(pickPhoto);



                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void videoSubscriptionPrompt(Context context, ActivityResultLauncher<Intent> launcher){
        Log.d("i am called", "videoSubscriptionPrompt: ");
        new MaterialAlertDialogBuilder(context)
                .setTitle("Video")
                .setMessage("This is an Optional Service %n You will be charged $1")
               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
            }
        }).setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectVideoFromGallery(context,launcher);


            }
        }).show();




    }

    public void selectVideoFromGallery(Context context, ActivityResultLauncher<Intent> launcher ){

        final CharSequence[] options = {"Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                Log.d("i am scalled", "videoSubscriptionPrompt: ");

                if (options[item].equals("Choose from Gallery")) {
                    Intent intent;
                    if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                    {
                        Log.d("iamascalled", "videoSubscriptionPrompt: ");

                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    }
                    else
                    {
                        Log.d("iambcalled", "videoSubscriptionPrompt: ");

                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                    }
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.putExtra("return-data", true);
                    launcher.launch(intent);
                }else if (options[item].equals("Cancel")) {
                    dialog.dismiss();

                }
            }

        });
        builder.show();
    }


}
