package com.example.assignment4_supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class World {
    private Handler handler;
    private int width, height;
    private int[][] block;
    private int xX = Obstacle.BLOCKWIDTH;
    private int z;
    private GameView game;


    public World(Bitmap level, GameView game){
        //this.handler = handler;
        this.game = game;
        loadWorld(level);

    }

    public void update(){
//        xX -= 5;
//        if(xX < -GameView.WIDTH){
//            //   xX = 0;
//        }
       // if(game.getGameCamera().getxOffset()>=91){

        //}
        shiftArray();
    }

    public void draw(Canvas canvas){

        for(int x = 0; x < width; x++){
            for(int y = height - 1; y > height - 11; y--) {
                Obstacle m = getObstacle(x,y);
                if(!(m == null)){
//                    getObstacle(x, y).draw(canvas, (int) (x * Obstacle.BLOCKWIDTH), y * Obstacle.BLOCKHEIGHT);
                    getObstacle(x, y).draw(canvas, (int) (x * Obstacle.BLOCKWIDTH - game.getGameCamera().getxOffset()), (int) (y * Obstacle.BLOCKHEIGHT));

//                    if (xX >= -GameView.WIDTH && xX < 0) {
//
//                        getObstacle(x, y).draw(canvas, x * Obstacle.BLOCKWIDTH + GameView.WIDTH + xX, y * Obstacle.BLOCKHEIGHT);
//                    }
                }
            }
        }
    }

    public Obstacle getObstacle(int x, int y){
        Obstacle t = Obstacle.blocks[block[x][y]];
        if(t == Obstacle.brick){
            return Obstacle.brick;
        }
        else if(t == Obstacle.floor){
            return Obstacle.floor;
        }
        else if(t == Obstacle.coin){
            return Obstacle.coin;
        }
        return null;
    }
    private void loadWorld(Bitmap level){
        //width of screen is 22
        width = 110;
        height = 12;
        block = new int[width][height];
        //setting floor
        for(int x = 0; x < width; x++){
            for(int y = height - 1; y > height - 3; y--) {
                block[x][y] = 1;
            }
        }
        block[9][9]=2;
        block[11][7]=2;
        block[12][7]=2;
        block[13][7]=2;
        block[11][4]=2;
        block[12][4]=2;
        block[11][5]=3;
        block[12][5]=3;

    }

    public int[][] getArray(){
        return block;
    }

    public void shiftArray() {
        int xShift = (int) (game.getGameCamera().getxOffset());
        //xShift = xShift/ (int) (game.getGameCamera().getxOffset());
        xX++;
        System.out.println(xX);

        if(xX > 2*Obstacle.BLOCKWIDTH){
        //if (xShift / Obstacle.BLOCKWIDTH > 0) {
        System.out.println(xX);
        xX = Obstacle.BLOCKWIDTH;
            for (int i = 0; i < width - 1; i++) {
                for (int j = 0; j < height; j++) {
                    block[i][j] = block[i+ 1][j];
                }
            }
        }
    }
}
