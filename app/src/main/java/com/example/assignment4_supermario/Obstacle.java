package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Obstacle extends Object{
    //static stuff here
    public static Obstacle[] blocks = new Obstacle[10];
    public static Obstacle brick = new Brick(2);
    public static Obstacle floor = new Floor(1);
    public static Obstacle coin = new Coin(3);

    //class
    private boolean playing;
    public static final int BLOCKWIDTH = 91, BLOCKHEIGHT =91;
    protected Bitmap block;
    private int screenHeight = 1080;
    public final int id;

    public Obstacle(Bitmap block,int id){
        this.id = id;
        this.block = block;

        blocks[id] = this;
    }

    public void draw(Canvas canvas, int i, int j){
        canvas.drawBitmap(block,i,j,null);
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
    public void characterCollide(Character character){

        Rect r1 = getRectangle(character.x, character.y, character.width, character.height);
        Rect r2 = getRectangle(x, y, width, height);

//        if(r2.contains(r1.left,r1.top) || r2.contains(r1.right,r1.top) || r2.contains(r1.left, r1.bottom) || r2.contains(r1.right, r1.bottom)){
//
//        }

    }
}
