package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Character {
    private Bitmap image1;
    public int x, y;

    private int screenHeigh = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Character(Bitmap bmp){
        image1 = bmp;
        y = screenHeigh - 220;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image1,x,y,null);
    }

    public void update(){
        // here we can make mario move by himself
    }


}
