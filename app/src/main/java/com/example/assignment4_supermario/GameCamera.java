package com.example.assignment4_supermario;

public class GameCamera {
    private float xOffset;

    private GameView game;

    public GameCamera(float xOffset){
        this.xOffset = xOffset;
    }

    public void move(float xAmt){
        xOffset += xAmt;
    }


    /*************** ABDUL *************************/
    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }
    public void centerOfCharacter(Character c){
        xOffset = c.x - game.WIDTH / 2;
       // System.out.println(xOffset);
    }
}
