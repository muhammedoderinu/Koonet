package com.hfad.koonect.entity;

import android.widget.Button;

public class Product {
    byte[] imageOne;
    byte[] imageTwo;
    byte[] imageThree;
    byte[] imageFour;
    String productName;
    String productCategory, productId;
    String price, videoUrl, thumbnail1Url,thumbnail2Url, thumbnail3Url, thumbnail4Url;
    boolean isVideo, isOld, isNew;


    public Product( String productName, String productCategory, String price, String productId,
                   String videoUrl, String thumbnail1Url, String thumbnail2Url, String thumbnail3Url,
                    String thumbnail4Url, boolean isVideo){

        this.productName = productName;
        this.price = price;
        this.productId= productId;
        this.productCategory = productCategory;
        this.videoUrl = videoUrl;
        this.thumbnail1Url = thumbnail1Url;
        this.thumbnail2Url = thumbnail2Url;
        this.thumbnail3Url = thumbnail3Url;
        this.thumbnail4Url = thumbnail4Url;
        this.isVideo = isVideo;


    }

    public Product( String productName, String productCategory, String price, String productId,
                   String thumbnail1Url,
                   String thumbnail2Url, String thumbnail3Url, String thumbnail4Url,
                    boolean isVideo,boolean isOld, boolean isNew ){

        this.productName = productName;
        this.price = price;
        this.productId= productId;
        this.productCategory = productCategory;
        this.thumbnail1Url = thumbnail1Url;
        this.thumbnail2Url = thumbnail2Url;
        this.thumbnail3Url = thumbnail3Url;
        this.thumbnail4Url = thumbnail4Url;
        this.isVideo = isVideo;
        this.isOld = isOld;
        this.isNew = isNew;


    }




    public byte[] getImageOne(){
        return imageOne;
    }

    public byte[] getImageTwo(){
        return imageTwo;
    }

    public byte[] getImageThree(){
        return imageThree;

    }

    public byte[] getImageFour(){
        return imageFour;
    }

    public String getProductName(){
        return productName;
    }

    public String getProductCategory(){
        return productCategory;
    }

    public String getPrice(){
        return price;
    }

    public String getProductId(){
        return productId;
    }

    public String getThumbnail1Url(){
        return thumbnail1Url;
    }

    public String getThumbnail2Url(){
        return thumbnail2Url;
    }

    public String getThumbnail3Url(){
        return thumbnail3Url;
    }

    public String getThumbnail4Url(){
        return thumbnail4Url;
    }

    public boolean getIsOld(){
        return isOld;
    }

    public boolean getIsNew(){
        return isNew;
    }

}
