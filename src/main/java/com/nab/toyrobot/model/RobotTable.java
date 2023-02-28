package com.nab.toyrobot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nab.toyrobot.serialize.NameSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component

public class RobotTable implements  Table{

    // private static variable to hold the singleton instance
    private static volatile Table instance;

    @JsonIgnore
    private int length;
    @JsonIgnore
    private int breadth;
    private Set<ToyRobot> robots = new HashSet<>();

//    @JsonIgnore
//    private Map<String, int[]> directionMap;


    // private constructor to prevent instantiation from outside
    private RobotTable(int length, int breadth)  {
        this.length = length;
        this.breadth = breadth;

        populateDirection();
    }


    @JsonProperty("activeRobotId")
    @JsonSerialize(using = NameSerializer.class)
    private int activeRobotId;

    @Override
    public boolean isOnTable(int x, int y) {
        return x >= 0 && x < getBreadth() && y >= 0 && y < getLength();
    }

    @Override
    public boolean detectCollision(int x, int y) {
        return robots.stream().anyMatch(e -> e.getPosition().equals(RobotPosition.builder().x(x).y(y).direction(e.getPosition().getDirection()).build()));
    }
    @Override
    public Set<Robot> report()
    {
        HashSet<Robot> robotList = new HashSet<>();
        robotList.addAll((Collection<? extends Robot>) getRobots());
        return robotList;
    }

    // public static method to get the singleton instance

    public static Table getInstance(int length, int breadth) {
        if (instance == null) { // check if the instance is not already created
            synchronized (Table.class) { // acquire lock on the class object
                if (instance == null) { // double check for thread-safety
                    instance = new RobotTable(length, breadth); // create the singleton instance
                }
            }
        }
        return instance; // return the singleton instance
    }

    public void populateDirection() {
        // Load the direction-to-position-change map from the properties file
        Properties prop = new Properties();
        InputStream input = null;
        try {
            File file = ResourceUtils.getFile("classpath:direction.properties");
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, int[]> directionMap = new HashMap<>();
        for (String key : prop.stringPropertyNames()) {
            String[] value = prop.getProperty(key).split(",");
            int[] positionChange = new int[] { Integer.parseInt(value[0]), Integer.parseInt(value[1]) };
            directionMap.put(key, positionChange);
        }

        //setDirectionMap(directionMap);
    }


}
