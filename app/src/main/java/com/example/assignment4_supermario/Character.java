package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
public class Character extends Object{
    private Bitmap sprite;
    private int character;
    private double dxa;
    private int leftSpeed = -8;
    private int rightSpeed = 8;
    private int upSpeed = 18;
    private double gravity = -3;
    private double t = 0.0;
    private boolean left, right, jump, down;
    private boolean playing;
    private int collision, dya;
    private Animation animation = new Animation();
    private int futx, futy; //used for predicting x and y
    private int position = 200;
    private int screenHeight = 1080;

    public Character(Bitmap bmp,int w, int h, int numFrames, int character){
        x = 100;
        collision = 0;
        this.character = character;
        y = screenHeight - position;
        dy = 0;
        dya = 0;
        height=h;
        width = w;
        Bitmap[] image = new Bitmap[numFrames];
        sprite = bmp;

        for(int i =0; i < numFrames; i++ ){
            image[i] = Bitmap.createBitmap(sprite, i*width, 0, width,height);
        }

        animation.setFrames(image,character);
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
            if(character == 1) {
                animation.setFrame(10);
            }
            else if (character ==2 ){
                animation.setFrame(14);
            }
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
            setDown(false);
        }
        jump = b;

    }
    public void setDown(boolean b){
        down = b;

    }
    public void setCollision(int side, int dya){
            this.dya = dya;
            collision = side;
            System.out.println(collision);
        }
    public void draw(Canvas canvas){
            canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void update(){
        // here we can make mario move by himself
        animation.update();
        if(right){
            dx = (int)(dxa+= 1);
            futx = getFutX(x);
            if(dx > rightSpeed){
                dx =rightSpeed;
            }
            if (futx > 960|| collision == 3){
                dx = 0;
            }
        }
        if(left){
            dx =(int)(dxa-= 1);
            futx = getFutX(x);
            if (dx < leftSpeed){
                dx =leftSpeed;
            }
            if (futx <= 0 || collision == 4){
                dx =0;
            }
        }
        x +=dx;
        dx = 0;
        if(jump) {
            if (collision == 0 ) {
                dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
                futy = getFutY(y);
                if (futy > screenHeight - position) {
                    dy = 0;
                    y = screenHeight - position;
                    t = 0;
                    jump = false;
                    down = true;
                    animation.setFrame(0);
                    animation.setJump(false);
                }
            }
            else if (collision == 2) {
                dy = 0;
                y = dya - height;
                t = 0;
                jump = false;
                down = true;
                animation.setFrame(0);
                animation.setJump(false);
            }
            else if(collision == 1 ){
                dy = 0;
                t = 0;
                jump =false;
                down = true;
            }
            t += 0.1;
            y -= dy;
            dy =0;
        }
        if(down){
            if(collision == 0) {
                dy = (int) (0.5 * gravity * t * t);
                futy = getFutY(y);
                if (futy > screenHeight - position) {
                    dy = 0;
                    y = screenHeight - position;
                    t = 0;
                    down = false;
                }
            }
            else if (collision == 2) {
                dy = 0;
                y = dya - height;
                down = false;
                t = 0;
            }
            t += 0.2;
            y -= dy;
            dy =0;

        }

    }
    public boolean getPlaying(){
        setDown(true);
        return playing;
    }
    public void setPlaying(boolean b){
        playing = b;
    }
    public void resetDXA(){
        dxa = 0;
    }

    public int getFutX(int num){
        int tmp = num;
        int tmpx =0;
        if(right){
            tmpx = tmpx += 1;
        }
        if(left){
            tmpx =tmpx-= 1;
        }
        tmp += tmpx;
        return tmp;

    }
    public int getFutY(int num) {
         int tmp = num;
         int tmpy = 0;
        if(jump) {
            tmpy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
            tmp -= tmpy;
        }
        if(down){
            tmpy = (int) (0.5 * gravity * t * t);
            tmp -= tmpy;
        }
        return tmp;
    }
}
