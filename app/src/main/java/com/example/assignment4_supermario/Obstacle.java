package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Obstacle {

    private boolean playing;
    private Bitmap image1;
    public int xX, yY;
    private int screenHeight = 1080;

    public Obstacle(Bitmap bmp, int x, int y){
        image1 = bmp;
        xX = x;
        yY = y + screenHeight/2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image1,xX,yY,null);
    }
    public void update(){
        //update anything
        if(playing) {
            xX -= 5;
            if (xX <= 0) {
                xX = GameView.WIDTH;
            }
        }
    }
    public void setPlaying(boolean b){

        playing = b;
    }
}
