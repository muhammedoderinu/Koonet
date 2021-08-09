package com.hfad.koonect.entity;

import android.widget.Button;

public class Product {
    byte[] imageOne;
    byte[] imageTwo;
    byte[] imageThree;
    byte[] imageFour;
    String productName;
    String productCategory;


    public Product(String productName, String productCategory){

        this.imageOne = imageOne;
        this.imageTwo = imageTwo;
        this.imageThree = imageThree;
        this.imageFour = imageFour;
        this.productName = productName;
        this.productCategory = productCategory;


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

}
