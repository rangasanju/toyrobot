package com.nab.toyrobot.model;

import com.nab.toyrobot.enums.Direction;

public interface Position {

    public RobotPosition getNextPosition(String direction);
    public Position turn(Rotation rotation);;
}
