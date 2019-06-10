package com.example.assignment4_supermario;

public class Starman extends Obstacle {
    public Starman(int id) {
        super(CreateBitmaps.starman, id);
    }

    @Override
    public int isSolid(){
        return 5;
    }
}
