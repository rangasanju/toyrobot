package com.nab.toyrobot.factory;

import com.nab.toyrobot.model.RobotTable;


public final class TableFactory {

    private TableFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static final RobotTable INSTANCE = RobotTable.builder().build();

    public static RobotTable getInstance() {
        return INSTANCE;
    }


}
