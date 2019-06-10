package com.example.assignment4_supermario;

import android.content.Context;

public class Handler {

    private GameView game;
    private World world;


    public Handler(GameView game) {
        this.game = game;
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public GameView getGame() {
        return game;
    }

    public void setGame(GameView game) {
        this.game = game;
    }

    public void setScore(int x) {
        game.score += x;
    }
    public void setLives(){
        game.lives -= 1;
    }
}
