package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long delay;
    private long startTime;
    private boolean played;

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
        long elasped = (System.nanoTime()-startTime)/100000;
        if(elasped > delay){
            currentFrame++;
            startTime= System.nanoTime();
        }
        if (currentFrame == frames.length-3){
            currentFrame = 1;
            played = true;
        }
    }

    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getFrame(){
        return currentFrame;
    }
    public boolean playedOnce(){return played;}
}
