package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Object {
    public static Bitmap brick,floor;
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

    public Rect getRectangle(int i, int j, int w, int h){
        return new Rect(i, j, i + w, j + h);
    }

    public static void init(){
        brick = Bitmap.createBitmap(brick,0,0,91,91);
        floor = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.floor);
    }
}
