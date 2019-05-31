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
    private Bitmap back; //
    private Bitmap cloud;
    public static int gapHeight = 600;
    public static int velocity = 150;
    public Obsatcle pipe1;
    public Character character;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public int x, y, xCloud;


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

        //We need to change this
        back = getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.sky),screenWidth,screenHeight);
        character = new Character(getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.small1), 100, 100));
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
        character.update();
        pipe1.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawBitmap(back,0,0,null);
            character.draw(canvas);

            pipe1.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        moveRight();
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
        int y, x;
       bmp = getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pipe),150,
              150);

        //bmp = BitmapFactory.decodeResource(getResources(),R.drawable.pipe2);
        pipe1 = new Obsatcle(bmp, 700, 100);
    }

    public void moveScreenLeft(){
        pipe1.xX -= velocity;
        if(pipe1.xX <= 0){
            pipe1.xX = screenWidth;
        }
    }

    public void moveRight(){
            character.x += 40;
            if(character.x >= screenWidth/2 - 200){
            moveScreenLeft();
            character.x = screenWidth/2 - 200;
        }
    }
}
