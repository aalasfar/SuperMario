package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
public class Character extends Object{
    private Bitmap smallsprite;
    private double dxa;
    private boolean left;
    private boolean right;
    private boolean playing;
    private Animation animation = new Animation();
    Canvas canvas;
    private int screenHeigh = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Character(Bitmap bmp,int w, int h, int numFrames){
        x = 100;
        y = screenHeigh - 300;
        dy = 0;
        height=h;
        width = w;
        Bitmap[] image = new Bitmap[numFrames];
        smallsprite = bmp;

        for(int i =0; i < numFrames; i++ ){
            image[i] = Bitmap.createBitmap(smallsprite, i*width, 0, width,height);
        }

        animation.setFrames(image);
        animation.setDelay(1000);


    }
    public void setRight(boolean b){
        if(!b){
            animation.setFrame(0);
        }
        right = b;}

    public void draw(Canvas canvas){
            canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void update(){
        // here we can make mario move by himself
        animation.update();

        if(!right){
            dx = (int)(dxa+= 1);
            System.out.println(dx);
        }
        if(dx > 5){
            dx =5;
        }

        if (x > 960){
            dx = 0;
        }
        x +=dx;
        dx = 0;
    }
    public boolean getPlaying(){

        return playing;
    }
    public void setPlaying(boolean b){

        playing = b;
    }
    public void resetDXA(){

        dxa = 0;
    }
}
