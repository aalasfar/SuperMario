package com.example.assignment4_supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class World {
    private Handler handler;
    private int width, height;
    private int[][] block;
    private int xX;
    private int z;


    public World(Bitmap level, Handler handler) {
        //this.handler = handler;
        this.handler = handler;
        loadWorld(level);

    }

    public void update() {
//        xX -= 5;
//        if(xX < -GameView.WIDTH){
//            //   xX = 0;
//        }
        // if(game.getGameCamera().getxOffset()>=91){

        //}
        //shiftArray();
    }

    public void draw(Canvas canvas) {

        //get the offset of camera to only display needed canvas
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset()/Obstacle.BLOCKWIDTH);
        int xEnd = (int) Math.min(width,(handler.getGameCamera().getxOffset()+handler.getWidth())/Obstacle.BLOCKWIDTH + 1);
        // the same thing could be done for y but we dont need it cuz y doesnt change

        for (int x = xStart; x < xEnd; x++) {
            for (int y = height-1; y > height - 13; y--) {
                Obstacle m = getObstacle(x, y);
                if (!(m == null)) {
//                    getObstacle(x, y).draw(canvas, (int) (x * Obstacle.BLOCKWIDTH), y * Obstacle.BLOCKHEIGHT);
                    getObstacle(x, y).draw(canvas, (int) (x * Obstacle.BLOCKWIDTH - handler.getGameCamera().getxOffset()), (int) (y * Obstacle.BLOCKHEIGHT));

//                    if (xX >= -GameView.WIDTH && xX < 0) {
//
//                        getObstacle(x, y).draw(canvas, x * Obstacle.BLOCKWIDTH + GameView.WIDTH + xX, y * Obstacle.BLOCKHEIGHT);
//                    }
                }
            }
        }
    }

    public Obstacle getObstacle(int x, int y) {
        Obstacle t = Obstacle.blocks[block[x][y]];
        if (t == Obstacle.brick) {
            return Obstacle.brick;
        } else if (t == Obstacle.floor) {
            return Obstacle.floor;
        } else if (t == Obstacle.coin) {
            return Obstacle.coin;
        }else if (t == Obstacle.supermushroom) {
            return Obstacle.supermushroom;
        }else if (t == Obstacle.starman) {
            return Obstacle.starman;
        }
        return null;
    }

    private void loadWorld(Bitmap level) {
        //width of screen is 22
        width = 110;
        height = 12;
        block = new int[width][height];
        //setting floor
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y > height -3; y--) {
                block[x][y] = 1;
            }
        }
        block[6][9] = 5;
        block[7][9]=3;
        block[8][9]=4;
        block[10][9] = 4;
        block[9][5] = 2;
    }

    public void editArray(int x, int y, int type){
        block[x][y] = type;
    }

    /************************ ABDUL **********************/
    public int[][] getArray() {
        return block;
    }


    public void shiftArray() {
        int xShift = (int) (handler.getGameCamera().getxOffset());
        //xShift = xShift/ (int) (game.getGameCamera().getxOffset());
        xX++;
       // System.out.println(xX);
        System.out.println((int)(handler.getGameCamera().getxOffset()) % Obstacle.BLOCKWIDTH);

        if(handler.getGameCamera().getxOffset() % Obstacle.BLOCKWIDTH == 0){
        //if (xShift / Obstacle.BLOCKWIDTH > 0) {
        xX = Obstacle.BLOCKWIDTH;
            for (int i = 0; i < width - 1; i++) {
                for (int j = 0; j < height; j++) {
                    block[i][j] = block[i+ 1][j];
                }
            }
        }
    }
}
