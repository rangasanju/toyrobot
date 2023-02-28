package com.nab.toyrobot.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class ConfigUtil {
    private static Map<String, List<Integer>> directionMap;
    private static Map<String, Integer> commands;
    private static Map<String, Integer> rotation;

    public static Map<String, List<Integer>> getDirectionMap() {
        return directionMap;
    }

    public static void setDirectionMap(Map<String, List<Integer>> directionMap) {
        ConfigUtil.directionMap = directionMap;
    }

    public static Map<String, Integer> getCommands() {
        return commands;
    }

    public static void setCommands(Map<String, Integer> commands) {
        ConfigUtil.commands = commands;
    }

    public static Map<String, Integer> getRotation() {
        return rotation;
    }

    public static void setRotation(Map<String, Integer> rotation) {
        ConfigUtil.rotation = rotation;
    }
}
