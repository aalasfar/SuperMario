package com.example.assignment4_supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    private Bitmap image;
    private int x,y,z;

    public Background(Bitmap img){
        image = img;
    }

    public void update(){
        x += z;
        if(x <-GameView.WIDTH){
            x=0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        if(x < 0){
            canvas.drawBitmap(image,x+GameView.WIDTH,y,null);
        }
    }
    public void setVector(int z){
        this.z = z;
    }
}
