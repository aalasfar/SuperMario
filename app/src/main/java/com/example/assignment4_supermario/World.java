package com.example.assignment4_supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class World {
    private Handler handler;
    private GameView game;
    private int width, height;
    private int[][] block;
    private int xX;
    private int z;


    public World(Bitmap world, Handler handler, int level) {
        //this.handler = handler;
        this.handler = handler;
        loadWorld(level);

    }

    public void update() {

        if(game.level == 2){

        }
    }

    public void draw(Canvas canvas) {

        //get the offset of camera to only display needed canvas
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset()/Obstacle.BLOCKWIDTH);
        int xEnd = (int) Math.min(width,(handler.getGameCamera().getxOffset()+handler.getWidth())/Obstacle.BLOCKWIDTH + 1);
        // the same thing could be done for y but we dont need it cuz y doesnt change

        if(xStart != xEnd) {

            for (int x = xStart; x < xEnd; x++) {
                for (int y = height - 1; y > height - 13; y--) {
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
        else{
            handler.getGameCamera().setxOffset(0);
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
        }else if (t == Obstacle.goomba) {
            return Obstacle.goomba;
        }else if (t == Obstacle.plant) {
            return Obstacle.plant;
        }else if (t == Obstacle.flag){
            return Obstacle.flag;
        }

        return null;
    }

    private void loadWorld(int level) {
        //width of screen is 22
        width = 110;
        height = 12;
        block = new int[width][height];
        //setting floor
        for (int x = 0; x < width; x++) {
            for (int y = 0; y <height; y++) {
                block[x][y] = 0;
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y > height - 3; y--) {
                block[x][y] = 1;
            }
        }
       block[100][1] = 8; //flag

        if(level == 1) {
            block[6][9] = 5;
            block[15][9] = block[16][9] = 0; //empty
            block[9][9] = block[14][6] = block[18][6] = block[40][7] = 3; //coin
            block[17][9] = 4; //mushroom
            block[4][9] = block[40][8] = 4;
            block[26][7] = block[27][7] = block[70][8]= block[40][9] = 2;
            block[10][9] =  block[17][9] = block[40][9] = 6;
            block[13][9] = block[4][9] = block[70][9] = 7;
        }
        else if(level == 2) {
            block[4][9] = 4;
            block[24][5] = 2;
            block[25][7] = 2;
        }

        else if(level == 3){
            block[6][9] = 5;
            block[9][9] = 3;

        }
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
