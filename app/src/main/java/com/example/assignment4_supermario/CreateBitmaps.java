package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CreateBitmaps {
    public static Bitmap brick,floor;
    private int id;


    public CreateBitmaps(Bitmap image, int id){
        this.id = id;
        if(id == 0){
            brick = image;
        }
        else if (id == 1){
            floor = image;
        }
    }
}


