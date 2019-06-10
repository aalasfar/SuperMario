package com.example.assignment4_supermario;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Matrix;



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

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int x, y, count;

    //Objects
    private MainThread thread;
    private Background back;
    private CreateBitmaps brick,floor, coin, supermushroom, starman;
    private Character mario, smallmario,bigmario;
    private World world;

    //Camera
    private GameCamera gameCamera;

    //Handler
    private Handler handler;

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
        handler = new Handler(this);
        count = 0;
        world = new World(CreateBitmaps.floor, handler);
        handler.setWorld(world);
        back = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.background));
        back.setVector(-5);
        setMario(1, 100, 680);
        setBitmaps();
        // GameCamera and Handler
        gameCamera = new GameCamera(0); //initialize its lcoation
        handler = new Handler(this);

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
                world.update();
                getGameCamera().move(5);
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
          back.draw(canvas);
          world.draw(canvas);
          mario.draw(canvas);
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
    }
    public void setMario(int type, int x, int y) {
        if(initial) {
            smallmario = new Character(handler, BitmapFactory.decodeResource(getResources(), R.drawable.smallsprites), x, y, smallmarioWidth, smallmarioHeight, 11, 1);
            bigmario = new Character(handler, BitmapFactory.decodeResource(getResources(), R.drawable.bigsprites), x, y - smallmarioHeight, bigmarioWidth, bigmarioHeight, 10, 2);
            initial = false;
        }
        if (type == 1 && !initial){
            smallmario.x = x;
            smallmario.y = y + smallmarioHeight;
            bigmario.setRight(false);
            bigmario.setDown(false);
            bigmario.setJump(false);
            bigmario.setLeft(false);
        }
        else if(type ==2 && ! initial){
            bigmario.x = x;
            bigmario.y = y - smallmarioHeight;
            smallmario.setRight(false);
            smallmario.setDown(false);
            smallmario.setJump(false);
            smallmario.setLeft(false);
        }
        SuperMario(type);
    }

    public void StarMario(int type){

    }
    public void setBitmaps(){
        brick = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.brick),2);
        floor = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.floor),1);
        coin = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.coin),3);
        supermushroom = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.supermushroom),4);
        starman = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.starman),5);
    }
    /********************************************/
    public GameCamera getGameCamera(){
        return gameCamera;
    }

    /********************************************/
}