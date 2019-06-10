package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Plant extends Obstacle{
    public Plant(int id) {
        super(CreateBitmaps.plant, id);
    }
    @Override
    public int isSolid(){
        return 6;
    }
}
