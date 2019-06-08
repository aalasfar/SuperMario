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
    final private int smallmarioHeight = 91;
    final private int bigmarioWidth = 100;
    final private int bigmarioHeight = 182;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int x, y, count;

    //Objects
    private MainThread thread;
    private Background back;
    private CreateBitmaps brick,floor, coin;
    private Character mario;
    private World world;
    private Handler handler;

    //Camera
    private GameCamera gameCamera;

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
        back = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.background));
        back.setVector(-5);
        mario = new Character(handler,BitmapFactory.decodeResource(getResources(),R.drawable.smallsprites),smallmarioWidth,smallmarioHeight,11,1);
        brick = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.brick),2);
        floor = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.floor),1);
        coin = new CreateBitmaps(BitmapFactory.decodeResource(getResources(),R.drawable.coin),3);
        count = 0;
        world = new World(CreateBitmaps.floor, this);
        handler.setWorld(world);

        gameCamera = new GameCamera(0); //initialize its lcoation
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
        if(mario.getPlaying()) {
            mario.update();
            //Obstacle.blocks[0].update();
            int[][] array;
            array = world.getArray();
            for(int i = 0; i > 110; i++){
                for (int j =0; j > 22; j++ ){
                    Collision(mario,Obstacle.blocks[array[i][j]]);
                }
            }
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
                if (i > 1680 && i < WIDTH) {
                    mario.setRight(true);
                    right = true;
                    //brick.setPlaying(true);
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
                if (j > 1680 && j < WIDTH) {
                    if(!left) {
                        mario.setRight(true);
                        right = true;
                      //  brick.setPlaying(true);
                        return true;
                    }
                }
                else if (j > 1440 && j < 1680){
                    if(!right) {
                        mario.setLeft(true);
                        left = true;
                        return true;
                    }
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
    /********************************************/
    public GameCamera getGameCamera(){
        return gameCamera;
    }

    /********************************************/

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
    public void Collision(Character c, Obstacle o){
        Rect r1 = c.getRectangle(c.x, c.y, c.width, c.height);
        Rect r2 = o.getRectangle(o.x, o.y, o.width, o.height);
        System.out.println(o.x);

        if(right){
            if(r2.contains(r1.top,r1.right) && r2.contains(r1.bottom,r1.right)){
                c.x = o.x - c.width;
                c.setCollision(3,o.y);
            }
        }
        if(left){

        }

    }

    /*public void moveScreenLeft(){
        brick.x -= velocity;
        if(brick.x <= 0){
            brick.x = screenWidth;
        }
    }*/

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
/*
public void Collision(Character character, Obstacle obstacle){
        int futy , futx;
        futy = character.getFutY(character.y);
        futx = character.getFutX(character.x);
        boolean collision = obstacle.characterCollide(futx, futy);
        if(collision) {
            //BOTTOM of Mario
            if (futy + character.height >= obstacle.y + obstacle.height || (futx + character.width >= obstacle.x && futx <= obstacle.x + obstacle.width)
            && futy < obstacle.y + obstacle.height/2) {
                character.setCollision(1,obstacle.y);
            }
            // TOP of Mario
            if ((futy <= obstacle.y + obstacle.height || (futx + character.width >= obstacle.x && futx <= obstacle.x + obstacle.width))
           && futy + character.height > obstacle.y + obstacle.height/2) {
                character.setCollision(2, obstacle.y);
            }

            /*if(futy + character.height >= obstacle.y && futy + character.height <= obstacle.y + 10 &&
                    (futx + character.width >= obstacle.x && futx + character.width <= obstacle.x + 10)
                    || (futx >= obstacle.x+obstacle.width - 10 && futx <= obstacle.x+obstacle.width)){
                character.setCollision(2, obstacle.y);
            }*/
//moving right
         /*   if (futx + character.width >= obstacle.x && futx + character.width < obstacle.x + 10 || (futy >= obstacle.y + obstacle.height &&
        futy + character.height <= obstacle.y)){
        character.setCollision(3,obstacle.y);
        }
        //moving left
        if(futx <= obstacle.x + obstacle.width && futx > obstacle.x - 10 || (futy > obstacle.y + obstacle.height&&
        futy + character.height < obstacle.y)){
        character.setCollision(4,obstacle.y);
        }

        }
        else {
        character.setCollision(0,obstacle.y);
        }
        }
 */