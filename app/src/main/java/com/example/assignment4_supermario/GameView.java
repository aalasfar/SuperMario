package com.example.assignment4_supermario;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Matrix;



public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Background back;
    private boolean right, left, jump;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    private Bitmap cloud;
    public static int gapHeight = 600;
    public static int velocity = 15;
    public Obstacle brick;
    private Character mario;
    final int smallmarioWidth = 91;
    final int smallmarioHeight = 91;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int x, y, count;





    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        back = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.background1));
        back.setVector(-5);
        mario = new Character(BitmapFactory.decodeResource(getResources(),R.drawable.smallsprites),smallmarioWidth,smallmarioHeight,11);
        count = 0;
        makeLevel();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }
    public void update(){
        if(mario.getPlaying()) {
            mario.update();
            logic();
            gravity(mario,brick);
            if(mario.x > WIDTH/2 && right ){
                back.update();
                brick.update();
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
          back.draw(canvas);
          mario.draw(canvas);
          brick.draw(canvas);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
                int i = (int) event.getX();
                if (!mario.getPlaying()) {
                    mario.setPlaying(true);
                }
                //System.out.println(i);
                if (i > 1680 && i < WIDTH) {
                    mario.setRight(true);
                    right = true;
                    brick.setPlaying(true);
                    return true;
                }

                else if (i > 1440 && i < 1680){
                    mario.setLeft(true);
                    left = true;
                    return true;
                }
                else if(i < 960){
                    mario.setJump(true);
                    jump = true;
                    return true;
                }
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                int j = (int) event.getX(pointerIndex);
                // System.out.println(j);
                if (j > 1680 && j < WIDTH) {
                    mario.setRight(true);
                    right = true;
                    brick.setPlaying(true);
                    return true;
                }
                else if (j > 1440 && j < 1680){
                    mario.setLeft(true);
                    left = true;
                    return true;
                }
                else if(j < 960){
                    mario.setJump(true);
                    jump = true;
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                break;
            }
            case MotionEvent.ACTION_UP:
                brick.setPlaying(false);
                if(right){
                    mario.setRight(false);
                    mario.resetDXA();
                    right = false;
                }
                else if (left) {
                    mario.setLeft(false);
                    left = false;
                    mario.resetDXA();
                }
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
            // mario.setPlaying(false);   return true;
        }

        return super.onTouchEvent(event);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight){
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        //RESIZE THE BITMAP
        matrix.postScale(scaleWidth,scaleHeight);
        //RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm,0,0,width,height,matrix,false);
        bm.recycle();
        return resizedBitmap;
    }

    /*********** logic for checking collision *******/
    public void logic(){
        boolean collision = brick.characterCollide(mario);
        if(collision){
            //BOTTOM
            if(mario.y + smallmarioHeight >= brick.yY + 100){
                mario.y = brick.yY + 100;
            }
            // TOP
            else if(mario.y <= brick.yY + 100){
                mario.setJump(false);
                mario.y = brick.yY - smallmarioHeight;
            }
        }

        /*
        if((mario.x+smallmarioWidth>=brick.xX && mario.x+smallmarioWidth <= brick.xX+150) || (mario.x>=brick.xX && mario.x <= brick.xX+150))
        {
            if (mario.y <= brick.yY + 150 && mario.y>= brick.yY) {
                mario.y = brick.yY + 150;
            }
            else if(mario.y+smallmarioHeight >= brick.yY){
                mario.y = brick.yY - smallmarioHeight;
                mario.setJump(false);
            }
        }*/
    }

    public void gravity(Character character, Obstacle obstacle){
        if((character.x > obstacle.xX + 100 || character.x+smallmarioWidth < obstacle.xX)&&character.y+smallmarioHeight<= obstacle.yY){
            character.setJump(true);
        }
    }


    public void makeLevel(){
        Bitmap bmp;
       bmp = getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.brick),100,
              100);
        brick= new Obstacle(bmp, 700, -20);
    }

    public void moveScreenLeft(){
        brick.xX -= velocity;
        if(brick.xX <= 0){
            brick.xX = screenWidth;
        }
    }

   /* public void moveRight(int c){
            //character.x += 40;
        if(c % 2 != 0){ mario = character[1];}
        else{ mario = character[2];}

        mario.walk();
            if(mario.x >= screenWidth/2 - 200){
            moveScreenLeft();
            mario.x = screenWidth/2 - 200;

            count++;
        }
    }*/
}
