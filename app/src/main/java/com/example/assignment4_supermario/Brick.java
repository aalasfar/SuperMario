package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Brick extends Obstacle {

    public Brick(int id) {
        super(Object.brick, id);
    }

    @Override
    public boolean isSolid(){
        return true;
    }
}
