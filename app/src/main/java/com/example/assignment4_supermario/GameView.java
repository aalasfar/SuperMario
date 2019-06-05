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
        mario = new Character(BitmapFactory.decodeResource(getResources(),R.drawable.smallsprites),193,183,13);
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
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int i = (int) event.getX();
            if (!mario.getPlaying()) {
                mario.setPlaying(true);
            }
            System.out.println(i);
            if (i > 1500 && i < WIDTH) {
                mario.setRight(true);
                right = true;
                brick.setPlaying(true);
                return true;
            }

            else if (i > 1000 && i < 1400){
                    mario.setLeft(true);
                    left = true;
                return true;
            }
            else if(i < 960){
                mario.setJump(true);
                jump = true;
            }
        }
        if(event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
            int i = (int) event.getX();
            System.out.println("Second touch detected");
            if(i < 960) {
                mario.setJump(true);
                jump = true;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
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
           // mario.setPlaying(false);

            return true;
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
    /*
    public void logic() {

//        if(characterSprite.x + 10 >= pipe1.xX){
//            Stop(characterSprite.x);
//        }

        /*
        //Detect if the character is touching one of the pipes
        if (characterSprite.y < pipe1.yY + (screenHeight / 2) - (gapHeight / 2)
                && characterSprite.x + 200 > pipe1.xX && characterSprite.x < pipe1.xX + 150)
        { resetLevel(); }

        if (characterSprite.y < pipe2.yY + (screenHeight / 2) - (gapHeight / 2)
                && characterSprite.x + 200 > pipe2.xX && characterSprite.x < pipe2.xX + 150)
        { resetLevel(); }

        if (characterSprite.y < pipe3.yY + (screenHeight / 2) - (gapHeight / 2)
                && characterSprite.x + 200 > pipe3.xX && characterSprite.x < pipe3.xX + 150)
        { resetLevel(); }

        if (characterSprite.y + 140 > (screenHeight / 2) + (gapHeight / 2) + pipe1.yY
                && characterSprite.x + 200 > pipe1.xX && characterSprite.x < pipe1.xX + 150)
        { resetLevel(); }

        if (characterSprite.y + 140 > (screenHeight / 2) + (gapHeight / 2) + pipe2.yY
                && characterSprite.x + 200 > pipe2.xX && characterSprite.x < pipe2.xX + 150)
        { resetLevel(); }

        if (characterSprite.y + 140 > (screenHeight / 2) + (gapHeight / 2) + pipe3.yY
                && characterSprite.x + 200 > pipe3.xX && characterSprite.x < pipe3.xX + 150)
        { resetLevel(); }
*/
        //Detect if the character has gone off the
        //bottom or top of the screen

      /*  if (characterSprite.y + 140 < 0) {
            resetLevel(); }
        if (characterSprite.y > screenHeight) {
            resetLevel(); }


        //If the pipe goes off the left of the screen,
        //put it forward at a randomized distance and height
        if (pipe1.xX + 150 < 0) {
            Random r = new Random();
            int value1 = r.nextInt(150);
            int value2 = r.nextInt(150);
            pipe1.xX = screenWidth + value1 + 100;
            pipe1.yY = value2 - 250;
            //
            xCloud = screenWidth - 200;
        }

        if (pipe2.xX + 150 < 0) {
            Random r = new Random();
            int value1 = r.nextInt(150);
            int value2 = r.nextInt(150);
            pipe2.xX = screenWidth + value1 + 100;
            pipe2.yY = value2 - 250;
        }

        if (pipe3.xX + 150 < 0) {
            Random r = new Random();
            int value1 = r.nextInt(150);
            int value2 = r.nextInt(150);
            pipe3.xX = screenWidth + value1 + 100;
            pipe3.yY = value2 - 250;
        }
    }
    */
    public void makeLevel(){
        Bitmap bmp;
       bmp = getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.brick),150,
              150);
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
