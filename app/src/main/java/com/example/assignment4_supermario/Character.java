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
    private int upSpeed = 18;
    private double gravity = -9.8;
    private double t = 0.0;
    private boolean left, right, jump, down;
    private boolean playing;
    private int collision;
    private Animation animation = new Animation();
    private int futx, futy; //used for predicting x and y
    private int position = 300;
    private int screenHeight = 1080;

    protected Rect bounds;
    protected Handler handler;
    private GameCamera cam;
    private GameView game;

    public Character(Handler handler, Bitmap bmp,int w, int h, int numFrames, int character){
        x = 100;
        this.handler = handler;
        collision = 0;
        this.character = character;
        y = screenHeight - position;
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
            int tx = (int)(x+ handler.getGameCamera().getxOffset() +width) / Obstacle.BLOCKWIDTH;

            if(!collisionwithTile(tx,(y)/Obstacle.BLOCKHEIGHT)
                    && !collisionwithTile(tx,((y + height )/Obstacle.BLOCKHEIGHT))){
                dx = (int)(dxa+= 1);
                if(dx > rightSpeed){
                    dx =rightSpeed;
                }
                if (x > 960){
                    dx = 0;
                }
                x +=dx;
                dx = 0;
            }
        }
        if(left){
           // moveLeft();
            int tx = (int)(x+ handler.getGameCamera().getxOffset()) / Obstacle.BLOCKWIDTH;
            if(!collisionwithTile(tx,(y)/Obstacle.BLOCKHEIGHT)
                    && !collisionwithTile(tx,((y + height )/Obstacle.BLOCKHEIGHT))){
                dx =(int)(dxa-= 1);
            }
            if (dx < leftSpeed){
                dx =leftSpeed;
            }
            if (x <= 0){
                dx =0;
            }
            x +=dx;
            dx = 0;

        }
        if(jump) {
           // moveUp();
            t += 0.1;
            if(t > 0 && t < 1.8) {
                int ty = y / Obstacle.BLOCKHEIGHT;
                System.out.println("between 0 and 1.8");
                if (!collisionwithTile((x + (int) (handler.getGameCamera().getxOffset())) / Obstacle.BLOCKWIDTH, ty)
                        && !collisionwithTile((x + width + (int) (handler.getGameCamera().getxOffset() - 1)) / Obstacle.BLOCKWIDTH, ty)) {
                    dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
                    System.out.println(t);
                    y -= dy;
                    dy = 0;

                }
            }
            else if (t > 1.9 && t <3 ){
                int ty = y + height/ Obstacle.BLOCKHEIGHT;
                System.out.println("between 1.8 and 3");
                if(!collisionwithTile((x + (int)(handler.getGameCamera().getxOffset()))/Obstacle.BLOCKWIDTH,ty)
                        && !collisionwithTile((x + width + (int)(handler.getGameCamera().getxOffset() -1))/Obstacle.BLOCKWIDTH,ty)){
                    dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
                    System.out.println(t);
                    y -= dy;
                    dy = 0;
                }
                else{
                    System.out.println("stop");
                    jump = false;
                    animation.setFrame(0);
                    animation.setJump(false);

                }


            }
//            if (collision == 0 ) {
//                dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
//                futy = getFutY(y);
//                if (futy > screenHeight - position) {
//                    dy = 0;
//                    y = screenHeight - position;
//                    t = 0;
//                    jump = false;
//                    down = true;
//                    animation.setFrame(0);
//                    animation.setJump(false);
//                }
//            }
//            else if (collision == 2) {
//                dy = 0;
//                y = dya - height;
//                t = 0;
//                jump = false;
//                down = true;
//                animation.setFrame(0);
//                animation.setJump(false);
//            }
//            else if(collision == 1 ){
//                dy = 0;
//                t = 0;
//                jump =false;
//                down = true;
//            }
//            t += 0.1;
//            y -= dy;
//            dy =0;
        }
        if(down){
            System.out.println("inside down");
            int ty = y + height/ Obstacle.BLOCKHEIGHT;
            System.out.println(ty);
            if(!collisionwithTile((x + (int)(handler.getGameCamera().getxOffset()))/Obstacle.BLOCKWIDTH,ty)
                    && !collisionwithTile((x + width + (int)(handler.getGameCamera().getxOffset() -1))/Obstacle.BLOCKWIDTH,ty)){
                System.out.println("Checking");
                futy = getFutY(y);
                dy = (int) (0.5 * gravity * t * t);
                if (futy > screenHeight - position) {
                    dy = 0;
                    y = screenHeight - position;
                    t = 0;
                    down = false;
                    animation.setFrame(0);
                    animation.setJump(false);
                }

                t += 0.2;
                y -= dy;
                dy = 0;
            }
            else{
                t =0;
                down = false;
                animation.setFrame(0);
                animation.setJump(false);
            }

        }
        /******************/
        //cam.centerOfCharacter(this);
        /******************/
    }

    public void moveDown(){
        int ty = y - height/ Obstacle.BLOCKHEIGHT;

        if( !collisionwithTile((x + (int)(handler.getGameCamera().getxOffset()))/Obstacle.BLOCKWIDTH,ty)
                && !collisionwithTile((x + width + (int)(handler.getGameCamera().getxOffset() -1))/Obstacle.BLOCKWIDTH,ty)) {
            dy = (int) ((upSpeed * t) + (0.5 * gravity * t * t));
            futy = getFutY(y);
            System.out.println(dy);
            System.out.println(t);
            if (futy > screenHeight - position) {
                dy = 0;
                y = screenHeight - position;
                t = 0;
                down = false;
                animation.setFrame(0);
                animation.setJump(false);
            }
            t += 0.1;
            y -= dy;
            dy = 0;
        }
        else{
            t =0;
            down = false;
            animation.setFrame(0);
            animation.setJump(false);
        }


    }


    protected boolean collisionwithTile(int x, int y){
        Obstacle check = handler.getWorld().getObstacle(x,y);
        if (check == null)
            return false;
        else {
            return handler.getWorld().getObstacle(x, y).isSolid();
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

    /************* ABDUL *************/
    public int xStart(){
        return (int) (cam.getxOffset()/Obstacle.BLOCKWIDTH);
    }

}
