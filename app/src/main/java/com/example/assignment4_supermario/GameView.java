package com.example.assignment4_supermario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;




public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private boolean right, left, jump;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    final private int smallmarioWidth = 91;
    final private int smallmarioHeight = 90;
    final private int bigmarioWidth = 97;
    final private int bigmarioHeight = 181;
    boolean initial = true;
    boolean start = true;
    private long starttime,endtime,deltatime;

    public int count;

    public long score = 0;
    public int lives = 3;
    public static int level = 0;

    //Objects
    private MainThread thread;
    private Background back;
    private Bitmap rightArrow, leftArrow, upArrow;
    private CreateBitmaps brick,floor, coin, supermushroom, starman, goomba,plant,flag;
    private Character mario, smallmario,bigmario,bstarmario, starmario;
    private World world1,world2,world3;
    public SurfaceHolder holder;

    //Camera
    private GameCamera gameCamera;

    //Handler
    private Handler handler;

    //Canvas




    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
    }
    public void setLevel(int type){
        level++;
        if(level == 1){
            handler.setWorld(world1);
            setMario(type, 100, 680);
            back = new Background(setLevelMap(level));
            back.setVector(-5);
            gameCamera = new GameCamera(0); //initialize its lcoation
            getGameCamera().move(0);
        }
        if(level == 2){
            handler.setWorld(world2);
            setMario(type, 100, 680);
            back = new Background(setLevelMap(level));
            back.setVector(-5);
            gameCamera = new GameCamera(0); //initialize its lcoation
            getGameCamera().move(0);
        }
        if(level == 3){
            handler.setWorld(world3);
            setMario(type, 100, 680);
            back = new Background(setLevelMap(level));
            back.setVector(-5);
            gameCamera = new GameCamera(0); //initialize its lcoation
            getGameCamera().move(0);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
        handler = new Handler(this);
        count = 0;
        starttime =0;
        endtime=0;
        deltatime =0;
        world1 = new World(handler, 1);
        world2 = new World(handler, 2);
        world3 = new World(handler, 3);
        setLevel(1);
        setBitmaps();
        // GameCamera and Handler
        gameCamera = new GameCamera(0); //initialize its lcoation
        rightArrow = BitmapFactory.decodeResource(getResources(),R.drawable.rightarrow);
        leftArrow = BitmapFactory.decodeResource(getResources(),R.drawable.leftarrow);
        upArrow =  BitmapFactory.decodeResource(getResources(),R.drawable.uparrow);
       getGameCamera().move(0);
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

        if(start) {
            mario.setPlaying(true);
            start = false;
        }
        if(mario.getPlaying()) {
            mario.update();
            //Obstacle.blocks[0].update();
            if(mario.x >= WIDTH/2 && right ){
                back.update();
                getGameCamera().move(5);
            }
        }
        if(lives <= 0){
            lives = 0;
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
          back.draw(canvas);

          if(level == 1) {
              world1.draw(canvas);
          }
          if(level == 2) {
              world2.draw(canvas);
          }
          if(level == 3) {
              world3.draw(canvas);
          }
          if(level == 4){
              displayWin(canvas);
              level = 0;
              thread.setRunning(false); //stops thread
          }
          mario.draw(canvas);
          displayScore(canvas);
            canvas.drawBitmap(rightArrow,300,950,null);
            canvas.drawBitmap(leftArrow,200,950,null);
            canvas.drawBitmap(upArrow,1500,950, null);


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
                if (i > 240 && i < 480) {
                    mario.setRight(true);
                    right = true;
                    //brick.setPlaying(true);
                    return true;
                }

                else if (i > 0 && i < 240){
                    mario.setLeft(true);
                    left = true;
                    return true;
                }
                else if(i > 960){
                    mario.setJump(true);
                    jump = true;
                    return true;
                }
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                int j = (int) event.getX(pointerIndex);
                if (j > 240 && j < 480) {
                    if(!left) {
                        mario.setRight(true);
                        right = true;
                      //  brick.setPlaying(true);
                        return true;
                    }
                }
                else if (j > 0 && j < 240){
                    if(!right) {
                        mario.setLeft(true);
                        left = true;
                        return true;
                    }
                }
                else if(j > 960){
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
                else if(jump){

                    jump = false;

                }
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
            //    return true;
        }

        return super.onTouchEvent(event);
    }

    public void SuperMario(int type){
        if(type == 1){
            mario = smallmario;
        }
        else if(type == 2){
            mario = bigmario;
        }
        else if(type == 3){
            mario = starmario;
        }
        else if(type == 4){
            mario = bstarmario;
        }
    }
    public void setMario(int type, int x, int y) {
        if(initial) {
            smallmario = new Character(handler, BitmapFactory.decodeResource(getResources(), R.drawable.smallsprites), x, y, smallmarioWidth, smallmarioHeight, 11, 1);
            bigmario = new Character(handler, BitmapFactory.decodeResource(getResources(), R.drawable.bigsprites), x, y - smallmarioHeight, bigmarioWidth, bigmarioHeight, 10, 2);
            bstarmario = new Character(handler, BitmapFactory.decodeResource(getResources(), R.drawable.bigstarsprites), x, y - smallmarioHeight, bigmarioWidth, bigmarioHeight, 10, 4);
            starmario = new Character(handler, BitmapFactory.decodeResource(getResources(), R.drawable.smallstarsprites), x, y, smallmarioWidth, smallmarioHeight, 11, 3);
        }

        if (type == 1 && !initial){
            if(mario.getCharacter() == 2 || mario.getCharacter() == 4){
                smallmario.x = x;
                smallmario.y = y + smallmarioHeight;
            }
            else if(mario.getCharacter() == 3 || mario.getCharacter() == 1) {
                smallmario.x = x;
                smallmario.y = y;
            }
            bigmario.setRight(false);
            bigmario.setDown(false);
            bigmario.setJump(false);
            bigmario.setLeft(false);
            starmario.setRight(false);
            starmario.setDown(false);
            starmario.setJump(false);
            starmario.setLeft(false);
            bstarmario.setRight(false);
            bstarmario.setDown(false);
            bstarmario.setJump(false);
            bstarmario.setLeft(false);
        }
        else if(type ==2 && ! initial){
            if(mario.getCharacter() == 1){
                bigmario.x = x;
                bigmario.y = y - smallmarioHeight;
            }
            else if(mario.getCharacter() == 4) {
                bigmario.x = x;
                bigmario.y = y;
            }
            smallmario.setRight(false);
            smallmario.setDown(false);
            smallmario.setJump(false);
            smallmario.setLeft(false);
            starmario.setRight(false);
            starmario.setDown(false);
            starmario.setJump(false);
            starmario.setLeft(false);
            bstarmario.setRight(false);
            bstarmario.setDown(false);
            bstarmario.setJump(false);
            bstarmario.setLeft(false);
        }
        else if(type == 3 && ! initial){
            if(mario.getCharacter() == 4){
                starmario.x = x;
                bstarmario.y = y + smallmarioHeight;
            }
            else if(mario.getCharacter() == 1) {
                starmario.x = x;
                starmario.y = y;
            }
            smallmario.setRight(false);
            smallmario.setDown(false);
            smallmario.setJump(false);
            smallmario.setLeft(false);
            bigmario.setRight(false);
            bigmario.setDown(false);
            bigmario.setJump(false);
            bigmario.setLeft(false);
            bstarmario.setRight(false);
            bstarmario.setDown(false);
            bstarmario.setJump(false);
            bstarmario.setLeft(false);

        }
        if (type == 4 && !initial){
            if(mario.getCharacter() == 3){
                bstarmario.x = x;
                bstarmario.y = y - smallmarioHeight;
            }
            else if(mario.getCharacter() == 2) {
                bstarmario.x = x;
                bstarmario.y = y;
            }
            bigmario.setRight(false);
            bigmario.setDown(false);
            bigmario.setJump(false);
            bigmario.setLeft(false);
            smallmario.setRight(false);
            smallmario.setDown(false);
            smallmario.setJump(false);
            smallmario.setLeft(false);
            starmario.setRight(false);
            starmario.setDown(false);
            starmario.setJump(false);
            starmario.setLeft(false);
        }
        SuperMario(type);
        initial = false;
    }
    public void setBitmaps(){
        brick = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.brick),2);
        floor = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.floor),1);
        coin = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.coin),3);
        supermushroom = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.supermushroom),4);
        starman = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.starman),5);
        goomba = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.goomba),6);
        plant = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.plant),7);
        flag = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.flag),8);

    }
    /********************************************/
    public GameCamera getGameCamera(){
        return gameCamera;
    }

    /********************************************/

    public void displayScore(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        canvas.drawText("SCORE " + score,1520, 70, paint);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(60);
        canvas.drawText("LIVES " + lives, 120, 70, paint);

        if(lives <= 0){
            lives = 0;
            displayGameOver(canvas);
            thread.setRunning(false);   //stop thread
        }

    }
    public void startClock(int time){
        deltatime = 0;
        if(time == 1) {
            starttime = System.currentTimeMillis();
            endtime = 0;
            deltatime = 0;
        }
        else{
            endtime = System.currentTimeMillis();
            deltatime = endtime - starttime;
        }
        deltatime = endtime - starttime;
        starMario();
    }

    public void starMario() {
        if (deltatime / 1000 >= 10) {
            if (mario.getCharacter() == 3) {
                setMario(1, mario.x, mario.y);
            } else if (mario.getCharacter() == 4) {
                setMario(2, mario.x, mario.y);

            }
        }
    }

    public Bitmap setLevelMap(int type){
        if(type == 1){
            return BitmapFactory.decodeResource(getResources(), R.drawable.background);
        }
        else if(type == 2){
            return BitmapFactory.decodeResource(getResources(),R.drawable.background2);
        }
            return BitmapFactory.decodeResource(getResources(), R.drawable.background3);
    }

    public void displayGameOver(Canvas canvas){
            Paint paint = new Paint();
           paint.setColor(Color.BLACK);
            paint.setTextSize(140);
           canvas.drawText("Game Over!", 500, 300, paint);
            paint.setTextSize(100);
            canvas.drawText("Score "+score, 500, 800, paint);
        }
    public void displayWin(Canvas canvas){
        canvas.drawARGB(255, 0, 0, 0);
        Paint paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setTextSize(140);
        canvas.drawText("Good Job!", 500, 300, paint2);
        paint2.setTextSize(100);
        canvas.drawText("Score "+score, 500, 800, paint2);
    }
}