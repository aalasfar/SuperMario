package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Object {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;

    public void SetX(int x){
        this.x = x;

    }
    public void SetY(int x){
        this.y = y;

    }
    public int GetX(int x){
        return x;

    }
    public int GetY(int x){
        return x;

    }
    public int GetWidth(int x){
        return width;

    }
    public int GetHeight(int x){
        return height;
    }

    public Rect getRectangle(){
        return new Rect(x,y,x+width,y+height);
    }
}
