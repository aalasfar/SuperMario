package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Floor extends Obstacle {
    public Floor(int id) {
        super(Object.floor, id);
    }

    @Override
    public boolean isSolid(){
        return true;
    }
}
