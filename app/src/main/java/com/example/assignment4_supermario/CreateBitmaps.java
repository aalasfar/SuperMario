package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CreateBitmaps {
    public static Bitmap brick,floor,coin, supermushroom, starman, goomba,plant, flag;

    private int id;


    public CreateBitmaps(Bitmap image, int id){
        this.id = id;
        if(id == 2){
            brick = image;
        }
        else if (id == 1){
            floor = image;
        }
        else if(id == 3){
            coin = image;
        }
        else if(id == 4){
            supermushroom = image;
        }
        else if(id == 5){
            starman = image;
        }
        else if(id == 6){
            goomba = image;
        }
        else if(id == 7){
            plant = image;
        }
        else if(id == 8){
            flag = image;
        }
    }
}


