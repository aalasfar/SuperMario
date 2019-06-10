package com.example.assignment4_supermario;

public class Brick extends Obstacle {

    public Brick(int id) {
        super(CreateBitmaps.brick, id);
    }

    @Override
    public int isSolid(){
        return 2;
    }
}
