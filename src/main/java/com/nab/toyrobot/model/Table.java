package com.nab.toyrobot.model;

import java.util.Set;

public interface Table {
    public boolean isOnTable(int x, int y);

    public boolean detectCollision(int x, int y);

    public Set<Robot> report();
}
