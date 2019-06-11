package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Flag extends Obstacle {
    public Flag(int id) {
        super(CreateBitmaps.flag,id);
    }

    @Override
    public int isSolid(){
        return 8;
    }

}
