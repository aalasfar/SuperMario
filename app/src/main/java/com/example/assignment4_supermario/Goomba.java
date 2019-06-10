package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Goomba extends Obstacle {

    public Goomba(int id) {
        super(CreateBitmaps.goomba, id);
    }
    @Override
    public int isSolid(){
        return 6;
    }
}
