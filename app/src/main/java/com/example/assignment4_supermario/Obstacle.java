package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Obstacle extends Object{
    //static stuff here
    public static Obstacle[] blocks = new Obstacle[256];
    public static Obstacle brick = new Brick(0);
    public static Obstacle floor = new Floor(1);

    //class
    private boolean playing;
    public static final int BLOCKWIDTH = 91, BLOCKHEIGHT =91;
    private Bitmap image;
    private int screenHeight = 1080;
    public final int id;

    public Obstacle(Bitmap image,int id){
        this.id = id;
        this.image = image;

        blocks[id] = this;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(image,x,y,null);
    }
    public void update(){
        //update anything
        if(playing) {
            x -= 5;
            if (x <= 0) {
                x = GameView.WIDTH;
            }
        }
    }

    public boolean isSolid(){
        return false;
    }

    public void setPlaying(boolean b){

        playing = b;
    }
    public boolean characterCollide(int posx, int posy){

        Rect r1 = getRectangle(posx, posy, 91, 91);
        Rect r2 = getRectangle(x, y, 91, 91);

        if(r2.contains(r1.left,r1.top) || r2.contains(r1.right,r1.top) || r2.contains(r1.left, r1.bottom) || r2.contains(r1.right, r1.bottom)){
            return true;
        }
//                if(r2.contains(r1.left,r1.top) || r2.contains(r1.right,r1.top)){
//                    return 1;
//                }
//                if(r2.contains(r1.left, r1.bottom) || r2.contains(r1.right, r1.bottom)){
//                    return 2;
//                }
        return false;
    }
}
