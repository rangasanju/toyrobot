package com.nab.toyrobot.model;

public interface Robot {
    public Robot move(Table table);

    public Robot turn(Table table, Rotation rotation) ;

}
