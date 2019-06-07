package com.example.assignment4_supermario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class World {
    private int width, height;
    private int[][] block;
    private int xX;
    private int z;

    public World(Bitmap level){
        loadWorld(level);

    }

    public void update(){
        xX -= 5;
        if(xX < -GameView.WIDTH){
         //   xX = 0;
        }
    }

    public void draw(Canvas canvas){
        for(int x = 0; x < width; x++){
            for(int y = height - 1; y > height - 3; y--) {
                Obstacle m = getObstacle(x, y);
                if (!(m == null)) {
                    getObstacle(x, y).draw(canvas, x * Obstacle.BLOCKWIDTH + xX, y * Obstacle.BLOCKHEIGHT);
                    if (xX >= -GameView.WIDTH && xX < 0) {
                        getObstacle(x, y).draw(canvas, x * Obstacle.BLOCKWIDTH + GameView.WIDTH + xX, y * Obstacle.BLOCKHEIGHT);
                    }
                }
            }
        }
    }

    public Obstacle getObstacle(int x, int y){
        Obstacle t = Obstacle.blocks[block[x][y]];
        if(t == null){   return null;}

            return Obstacle.brick;
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

    }
}
