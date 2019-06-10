package com.example.assignment4_supermario;

import android.graphics.Bitmap;

public class Animation {
    private Bitmap[] frames;
    private int character;
    private int currentFrame;
    private long delay;
    private long startTime;
    private boolean played;
    private boolean right;
    private boolean left;
    private boolean jump;

    public void setFrames(Bitmap[] frames, int character){
        this.frames= frames;
        this.character = character;
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
        if(character == 1 ) {
            if (right) {
                if (elapsed > delay) {
                    currentFrame++;
                    startTime = System.nanoTime();
                }
                if (currentFrame >= 4) {
                    currentFrame = 1;
                    played = true;
                }
            }
            if (left) {
                if (elapsed > delay) {
                    currentFrame--;
                    startTime = System.nanoTime();
                }
                if (currentFrame <= 7) {
                    currentFrame = 10;
                    played = true;
                }
            }
            if (jump) {
                if (right) {
                    currentFrame = 4;
                }
                if (left) {
                    currentFrame = 6;
                } else {
                    currentFrame = 4;
                }
            }
        }
        else if(character == 2){
            if (right) {
                if (elapsed > delay) {
                    currentFrame++;
                    startTime = System.nanoTime();
                }
                if (currentFrame >= 4) {
                    currentFrame = 1;
                    played = true;
                }
            }
            if (left) {
                if (elapsed > delay) {
                    currentFrame--;
                    startTime = System.nanoTime();
                }
                if (currentFrame <= 5) {
                    currentFrame = 8;
                    played = true;
                }
            }
            if (jump) {
                if (right) {
                    currentFrame = 4;
                }
                if (left) {
                    currentFrame = 5;
                } else {
                    currentFrame = 4;
                }
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
    public void setJump(boolean b){
        jump = b;}

    public int getFrame(){
        return currentFrame;
    }
    public boolean playedOnce(){return played;}
}
