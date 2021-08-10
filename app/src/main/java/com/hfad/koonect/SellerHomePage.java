package com.hfad.koonect;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.hfad.koonect.database.SellerDatabase;
import com.hfad.koonect.options.LogOut;
import com.hfad.koonect.options.SellerProfile;
import com.hfad.koonect.sellerhomepage.HomePage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class SellerHomePage extends AppCompatActivity {
    FrameLayout thumbnail1, thumbnail2, thumbnail3, thumbnail4, openDialog;
    ActivityResultLauncher<Intent> somehActivityResultLauncher;
    ActivityResultLauncher<Intent> somehActivityResultTwoLauncher, someVideoActivityResultLauncher;
    ImageView imageOne, imageTwo, imageThree, imageFour;
    int imageOn;
    Button publishProduct;
    TextView productNameRaw;
    AutoCompleteTextView productCategoryRaw;
    VideoView videoThumbnail;
    String[] categories =  {"Mobile","Electronics","Kitchen","Automobile","Housing"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home_page);
        whichImageOn(0);
        onhActivityResult();
        onhActivityResultTwo();
        onVideoResultActivity();
        publishProduct = findViewById(R.id.publishProduct);
        thumbnail1 = findViewById(R.id.thumbnail1);
        thumbnail2 = findViewById(R.id.thumbnail2);
        thumbnail3 = findViewById(R.id.thumbnail3);
        thumbnail4 = findViewById(R.id.thumbnail4);
        imageOne = findViewById(R.id.imageOne);
        imageTwo = findViewById(R.id.imageTwo);
        imageThree = findViewById(R.id.imageThree);
        imageFour = findViewById(R.id.imageFour);
        productNameRaw = findViewById(R.id.productName);
        productCategoryRaw = (AutoCompleteTextView)findViewById(R.id.productCategory);
        videoThumbnail = findViewById(R.id.videoThumbnail);
        openDialog = findViewById(R.id.openDialog);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item,categories);
        productCategoryRaw.setThreshold(1);
        productCategoryRaw.setAdapter(adapter);
        if(!videoThumbnail.isPlaying()) {
            openDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("this is me", "onClick: ");
                    HomePage homepage = new HomePage();
                    homepage.videoSubscriptionPrompt(SellerHomePage.this, someVideoActivityResultLauncher);
                }
            });
        }

        thumbnail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOn =  whichImageOn(1);
                Log.d("imageOne", String.valueOf(imageOn));
                HomePage homePage = new HomePage();
                homePage.selectThumbnails(SellerHomePage.this,somehActivityResultLauncher,
                        somehActivityResultTwoLauncher,SellerHomePage.this);




            }
        });

        thumbnail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage homePage = new HomePage();
                homePage.selectThumbnails(SellerHomePage.this,somehActivityResultLauncher,
                        somehActivityResultTwoLauncher,SellerHomePage.this);
              imageOn =   whichImageOn(2);

            }
        });

        thumbnail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage homePage = new HomePage();
                homePage.selectThumbnails(SellerHomePage.this,somehActivityResultLauncher,
                        somehActivityResultTwoLauncher,SellerHomePage.this);
                  imageOn =   whichImageOn(3);

            }
        });

        thumbnail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePage homePage = new HomePage();
                homePage.selectThumbnails(SellerHomePage.this,somehActivityResultLauncher,
                        somehActivityResultTwoLauncher,SellerHomePage.this);
               imageOn =  whichImageOn(4);

            }
        });

        publishProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameRaw.getText().toString();
                String productCategory = productCategoryRaw.getText().toString();
                SellerDatabase sellerDatabase = new SellerDatabase();
                sellerDatabase.uploadProductInfo(productName,productCategory);
                byte[] thumbnailImage1 = getProfilePics(imageOne);
                sellerDatabase.uploadThumbnailsToCloud(thumbnailImage1,"thumbnail1");
                byte[] thumbnailImage2 = getProfilePics(imageTwo);
                sellerDatabase.uploadThumbnailsToCloud(thumbnailImage2,"thumbnail2");
                byte[] thumbnailImage3 = getProfilePics(imageThree);
                sellerDatabase.uploadThumbnailsToCloud(thumbnailImage3,"thumbnail3");
                byte[] thumbnailImage4 = getProfilePics(imageFour);
                sellerDatabase.uploadThumbnailsToCloud(thumbnailImage4,"thumbnail4");

            }
        });





    }
    @Override
    public void onStart(){
        super.onStart();

    }

    public byte[] getProfilePics(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap =  ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        return true;
    }


    public int whichImageOn(int check){
        if(check==1){
            return 1;

        }else if(check==2){
            return 2;
        }else if(check==3){
            return 3;
        }
        else if(check==4){
            return 4;
        }
        return 0;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case(R.id.userProfile):
                SellerProfile sellerProfile = new SellerProfile();
                sellerProfile.openSellerProfilePage(getBaseContext());
                //do something
                return true;
            case(R.id.logOut):
                // log out
                LogOut logOut = new LogOut();
                logOut.logSellerOut(SellerHomePage.this);

                    return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onhActivityResult(){


        somehActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("i am called", "onActivityResult: ");

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            if (data.getData() == null) {
                                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                                if(imageOn==1) {
                                    imageOne.setImageBitmap(bitmap);
                                }
                                if(imageOn==2){
                                    imageTwo.setImageBitmap(bitmap);
                                }
                                if(imageOn==3){
                                    imageThree.setImageBitmap(bitmap);
                                }
                                if(imageOn==4){
                                    imageFour.setImageBitmap(bitmap);
                                }





                            } else {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap
                                            (SellerHomePage.this.getContentResolver(), data.getData());
                                    if(imageOn==1){
                                        imageOne.setImageBitmap(bitmap);
                                    }
                                    if(imageOn==2){
                                        imageTwo.setImageBitmap(bitmap);
                                    }
                                    if(imageOn==3){
                                        imageThree.setImageBitmap(bitmap);
                                    }
                                    if(imageOn==4){
                                        imageFour.setImageBitmap(bitmap);
                                    }


                                } catch (Exception e) {

                                }


                            }
                            //doSomeOperations();
                        }
                    }
                });


    }

    public void onhActivityResultTwo(){
                        Log.d("ok i am called", "onActivityResult: ");

                        somehActivityResultTwoLauncher = registerForActivityResult(
                                new ActivityResultContracts.StartActivityForResult(),
                                new ActivityResultCallback<ActivityResult>() {
                                    @Override
                                    public void onActivityResult(ActivityResult result) {
                                        Intent data = result.getData();
                                        Uri selectedImage = data.getData();
                                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                                        Cursor cursor = getContentResolver().query(selectedImage,
                                                filePathColumn, null, null, null);
                                        cursor.moveToFirst();
                                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                        String picturePath = cursor.getString(columnIndex);
                                        cursor.close();

                                        if(imageOn==1) {
                                            Picasso.get()
                                                    .load(picturePath)
                                                    .fit()
                                                    .into(imageOne);
                                        }

                                        if(imageOn ==2) {
                                            Picasso.get()
                                                    .load(picturePath)
                                                    .fit()
                                                    .into(imageTwo);

                                        }

                                        if(imageOn ==3) {
                                            Picasso.get()
                                                    .load(picturePath)
                                                    .fit()
                                                    .into(imageThree);

                                        }
                                        if(imageOn ==4) {
                                            Picasso.get()
                                                    .load(picturePath)
                                                    .into(imageFour);

                                        }
                                    }
                                });




    }

    public void onVideoResultActivity(){


        someVideoActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("oknowiammcalled", "onActivityResult: ");
                        Intent data = result.getData();

                        try
                        {
                            Toast.makeText(SellerHomePage.this,"hmnnn ", Toast.LENGTH_LONG)
                                    .show();
                            Uri path = data.getData();
                            MediaController controller = new MediaController(SellerHomePage.this);
                            controller.setAnchorView(SellerHomePage.this.videoThumbnail);
                            controller.setMediaPlayer(videoThumbnail);
                            videoThumbnail.setMediaController(controller);
                            videoThumbnail.setVideoURI(path);
                            videoThumbnail.requestFocus();
                            videoThumbnail.start();
                            if(videoThumbnail.isPlaying()) {
                                openDialog.setEnabled(false);
                            }


                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                });




    }

}