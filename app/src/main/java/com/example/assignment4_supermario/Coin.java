package com.example.assignment4_supermario;

public class Coin extends Obstacle {

    public Coin(int id) {
        super(CreateBitmaps.coin, id);

    }
    @Override
    public boolean isSolid(){
        return false;
    }
}
