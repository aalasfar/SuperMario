package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long delay;
    private long startTime;
    private boolean played;
    private boolean right;
    private boolean left;

    public void setFrames(Bitmap[] frames){
        this.frames= frames;
        currentFrame = 0;
    }

    public void setDelay(long d){
        delay = d;
    }

    public void setFrame(int i){
        currentFrame = i;
    }

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/100000;
        if(right) {
            if (elapsed > delay) {
                currentFrame++;
                startTime = System.nanoTime();
            }
            if (currentFrame >= 4) {
                currentFrame = 1;
                played = true;
            }
        }
        if(left) {
            if (elapsed > delay) {
                currentFrame--;
                startTime = System.nanoTime();
            }
            if (currentFrame <= 8) {
                currentFrame = 12;
                played = true;
            }
        }
    }

    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public void setRight(boolean b){
        right = b;}

    public void setLeft(boolean b){
        left = b;}

    public int getFrame(){
        return currentFrame;
    }
    public boolean playedOnce(){return played;}
}
