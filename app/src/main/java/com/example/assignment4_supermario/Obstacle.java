package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Obstacle {

    private boolean playing;
    private Bitmap image1;
    public int xX, yY;
    private int screenHeight =
            Resources.getSystem().getDisplayMetrics().heightPixels;

    public Obstacle(Bitmap bmp, int x, int y){
        image1 = bmp;
        xX = x;
        yY = y;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image1,xX,((screenHeight/2)+(GameView.gapHeight/2))+yY,null);
    }
    public void update(){
        //update anything
        if(playing) {
            xX -= 15;
            if (xX <= 0) {
                xX = GameView.WIDTH;
            }
        }
    }
    public void setPlaying(boolean b){

        playing = b;
    }
}
