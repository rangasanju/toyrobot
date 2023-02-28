package com.nab.toyrobot.util;

import java.util.List;

public class CommandUtil {
    private static List<String> commands;

    public static List<String> getCommands() {
        return commands;
    }

    public static void setCommands(List<String> commands) {
        CommandUtil.commands = commands;
    }
}
