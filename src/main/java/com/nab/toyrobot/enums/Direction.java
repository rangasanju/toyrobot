package com.nab.toyrobot.enums;

import com.nab.toyrobot.model.Rotation;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction nextDirection(Rotation rotation) {
        int nextIndex = rotation.equals(Rotation.LEFT) ? ordinal() - 1 : ordinal() + 1;
        if (nextIndex < 0) {
            nextIndex = values().length - 1;
        } else if (nextIndex >= values().length) {
            nextIndex = 0;
        }
        return values()[nextIndex];
    }
}