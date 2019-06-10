package com.example.assignment4_supermario;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public class Character extends Object{
    private Bitmap sprite;
    private int character;
    private double dxa;
    private int leftSpeed = -8;
    private int rightSpeed = 8;
    private int upSpeed = 15;
    private double gravity = -9.8;
    private double t = 0.0;
    private boolean left, right, jump, down;
    private boolean playing;
    private boolean collision;
    private Animation animation = new Animation();
    private int futx, futy; //used for predicting x and y
    private int position = 400;
    private int screenHeight = 1080;

    protected Rect bounds;
    protected Handler handler;
    private GameCamera cam;
    private GameView game;

    public Character(Handler handler, Bitmap bmp,int x, int y, int w, int h, int numFrames, int character){
        this.x= x;
        this.handler = handler;
        this.character = character;
        this.y = y;
        dy = 0;
        height = h;
        width = w;
        Bitmap[] image = new Bitmap[numFrames];
        sprite = bmp;
        bounds = new Rect(x,y,x+width,y+height);
        for(int i =0; i < numFrames; i++ ){
            image[i] = Bitmap.createBitmap(sprite, i*width, 0, width,height);
        }

        animation.setFrames(image,character);
        animation.setDelay(500);


    }
    public void setRight(boolean b){
        if(!b) {
            if (character == 1) {
                animation.setFrame(0);
            }
            else if(character == 2){
                animation.setFrame(0);
            }
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
                animation.setFrame(9);
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
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        bounds.left = x;
        bounds.top = y;
        bounds.right = x+width;
        bounds.bottom = y+height;
        canvas.drawRect(bounds,paint);
        canvas.drawBitmap(animation.getImage(), x, y, null);

//        bounds.left = x - (int) (handler.getGameCamera().getxOffset());
//        bounds.top = y;
//        bounds.right = x+width - (int) (handler.getGameCamera().getxOffset());
//        bounds.bottom = y+height;
//        canvas.drawRect(bounds,paint);
//        canvas.drawBitmap(animation.getImage(), (int)(x - handler.getGameCamera().getxOffset()), y, null);
       // System.out.println(handler.getGameCamera().getxOffset()+"   "+x);

    }

    public void update(){
        // here we can make mario move by himself
        animation.update();

        if(right){
            //moveRight();
            futx = getFutX(x);
            int tx = (int)(futx+ handler.getGameCamera().getxOffset() +width) / Obstacle.BLOCKWIDTH;
            if(collisionwithTile(tx,(y)/Obstacle.BLOCKHEIGHT) == 0
                    && collisionwithTile(tx,((y + height )/Obstacle.BLOCKHEIGHT)) == 0){
                dx = (int)(dxa+= 1);
                if(dx > rightSpeed){
                    dx = rightSpeed;
                }
                if (x > 960){
                    dx = 0;
                }
                x +=dx;
                dx = 0;
            }
            else {
                handler.getGame().setMario(2,x,y);
            }
            /*futy = getFutY(y);
            int ty = (futy + height) / Obstacle.BLOCKHEIGHT;
            if (!collisionwithTile((x + (int) (handler.getGameCamera().getxOffset())) / Obstacle.BLOCKWIDTH, ty)
                    && !collisionwithTile((x + width + (int) (handler.getGameCamera().getxOffset() - 1)) / Obstacle.BLOCKWIDTH, ty)){
                down = true;
            }*/
        }
        if(left){
           // moveLeft();
            futx = getFutX(x);
            int tx = (int)(futx+ handler.getGameCamera().getxOffset()) / Obstacle.BLOCKWIDTH;
            if(collisionwithTile(tx,(y)/Obstacle.BLOCKHEIGHT) == 0
                    && collisionwithTile(tx,((y + height )/Obstacle.BLOCKHEIGHT)) == 0){
                dx =(int)(dxa-= 1);
                if (dx < leftSpeed){
                    dx = leftSpeed;
                }
                if (x <= 0){
                    dx =0;
                }
                x +=dx;
                dx = 0;
            }
            else {
                handler.getGame().setMario(1,x,y);
            }
            /*futy = getFutY(y);
            int ty = (futy + height) / Obstacle.BLOCKHEIGHT;
            if (!collisionwithTile((x + (int) (handler.getGameCamera().getxOffset())) / Obstacle.BLOCKWIDTH, ty)
                    && !collisionwithTile((x + width + (int) (handler.getGameCamera().getxOffset() - 1)) / Obstacle.BLOCKWIDTH, ty)){
                down = true;
            }*/
        }
        if(jump) {
           // moveUp();
            t+=.1;
            futy = getFutY(y);
            //if (t > 0 && t < 1.6) {
                int ty1 = y / Obstacle.BLOCKHEIGHT;
                if (collisionwithTile((x + (int) (handler.getGameCamera().getxOffset())) / Obstacle.BLOCKWIDTH, ty1)== 0
                        && collisionwithTile((x + width + (int) (handler.getGameCamera().getxOffset() - 1)) / Obstacle.BLOCKWIDTH, ty1)==0) {
                    dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
                    y -= dy;
                    dy = 0;
                } else {
                    t = 0;
                    //y = ty1 * Obstacle.BLOCKHEIGHT + Obstacle.BLOCKHEIGHT;
                    jump = false;
                    down= true;
                    //animation.setFrame(0);
                    animation.setJump(false);
                }
            //}
            //else if (t > 1.6 && t <8 ) {
                int ty2 = (futy + height) / Obstacle.BLOCKHEIGHT;
                if (collisionwithTile((x + (int) (handler.getGameCamera().getxOffset())) / Obstacle.BLOCKWIDTH, ty2) == 0
                        && collisionwithTile((x + width + (int) (handler.getGameCamera().getxOffset())) / Obstacle.BLOCKWIDTH, ty2) == 0) {
                    dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
                    y -= dy;
                    dy = 0;
                }
                else {
                    y = ty2 * Obstacle.BLOCKHEIGHT - height - 1;
                    t =0;
                    animation.setFrame(0);
                    animation.setJump(false);
                    down = false;
                    jump =false;
                }
            //}
            if ( t > 8){
                t = 0;
                jump = false;
                animation.setFrame(0);
                animation.setJump(false);
            }
        }
        if(down){
            futy = getFutY(y);
            int ty = (futy + height)/ Obstacle.BLOCKHEIGHT;
            if(collisionwithTile((x + (int)(handler.getGameCamera().getxOffset()))/Obstacle.BLOCKWIDTH,ty) == 0
                    && collisionwithTile((x + width + (int)(handler.getGameCamera().getxOffset() -1))/Obstacle.BLOCKWIDTH,ty) == 0){
                dy = (int) (0.5 * gravity * t * t);
                t += 0.2;
                y -= dy;
                dy = 0;
            }
            else{
                if(!right || !left) {
                    y = ty * Obstacle.BLOCKHEIGHT - height - 1;
                }
               // y = ty * Obstacle.BLOCKHEIGHT - height -1;
                t =0;
                animation.setFrame(0);
                animation.setJump(false);
                down = false;
                jump =false;
                //animation.setFrame(0);
                //animation.setJump(false);
            }

        }
        /******************/
        //cam.centerOfCharacter(this);
        /******************/
    }
    protected int collisionwithTile(int x, int y){
        Obstacle check = handler.getWorld().getObstacle(x,y);
        if (check == null)
            return 0;
        else {
            return handler.getWorld().getObstacle(x, y).isSolid();
        }
    }

    public boolean getPlaying(){
        return playing;
    }
    public void setPlaying(boolean b){
        if(!animation.playedOnce()) {
            setDown(true);
        }
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
         double tmpt = t + .01;
         tmpy = (int) ((upSpeed * tmpt) + (0.5 * gravity * tmpt * tmpt));
         tmp -= tmpy;

        return tmp;
    }

    /************* ABDUL *************/
    public int xStart(){
        return (int) (cam.getxOffset()/Obstacle.BLOCKWIDTH);
    }

}
