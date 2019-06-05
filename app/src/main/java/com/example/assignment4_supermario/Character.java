package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
public class Character extends Object{
    private Bitmap smallsprite;
    private double dxa, dya;
    private int leftSpeed = -8;
    private int rightSpeed = 8;
    private int upSpeed = 15;
    private double gravity = -9.8;
    private double t = 0.0;
    private boolean left, right, jump;
    private boolean playing;
    private Animation animation = new Animation();
    Canvas canvas;
    private int screenHeight = 1080;

    public Character(Bitmap bmp,int w, int h, int numFrames){
        x = 100;
        y = screenHeight - 300;
        dy = 0;
        dya = 0;
        height=h;
        width = w;
        Bitmap[] image = new Bitmap[numFrames];
        smallsprite = bmp;

        for(int i =0; i < numFrames; i++ ){
            image[i] = Bitmap.createBitmap(smallsprite, i*width, 0, width,height);
        }

        animation.setFrames(image);
        animation.setDelay(500);


    }
    public void setRight(boolean b){
        if(!b){
            animation.setFrame(0);
            animation.setRight(false);
        }
        else if(b){
            animation.setRight(true);
        }
        right = b;}

    public void setLeft(boolean b){
        if(!b){
            animation.setFrame(10);
            animation.setLeft(false);
        }
        else if(b){
            animation.setLeft(true);
        }
        left = b;}

    public void setJump(boolean b){
        if (!b) {
            animation.setFrame(0);
            animation.setJump(false);
        }
        else if(b){
            animation.setJump(true);
        }
        jump = b;}

    public void draw(Canvas canvas){
            canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void update(){
        // here we can make mario move by himself
        animation.update();
        if(right){
            dx = (int)(dxa+= 1);
            if(dx > rightSpeed){
                dx =rightSpeed;
            }
            if (x > 960){
                dx = 0;
            }
        }
        if(left){
            dx =(int)(dxa-= 1);
            if (dx < leftSpeed){
                dx =leftSpeed;
            }
            if (x <= 0){
                dx =0;
            }
        }
        x +=dx;
        dx = 0;
        if(jump) {
            dy = (int)((upSpeed * t) + (0.5 * gravity * t * t));
            if (y > screenHeight - 300) {
                dy = 0;
                y = screenHeight -300;
                t = 0;
                jump = false;
                animation.setFrame(0);
                animation.setJump(false);
            }
            //logic();
            t += 0.03;
            y -= dy;
            System.out.println(y);
            dy =0;
        }
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
