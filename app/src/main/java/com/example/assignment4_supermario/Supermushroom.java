package com.example.assignment4_supermario;

public class Supermushroom extends Obstacle {
    public Supermushroom(int id) {
        super(CreateBitmaps.supermushroom, id);
    }

    @Override
    public int isSolid(){
        return 4;
    }
}
