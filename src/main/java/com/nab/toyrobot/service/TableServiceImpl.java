package com.nab.toyrobot.service;

import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.*;
import com.nab.toyrobot.util.ConfigUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Service
@Data
@ConfigurationProperties(prefix = "spring")
//@PropertySource("classpath:application.properties")
public class TableServiceImpl implements TableService{


    @Value("${table.length:5}")
    private int tableLength;

    @Value("${table.breadth:5}")
    private int tableBreadth;
    RobotTable table;

    private  Map<String, List<Integer>> directionMap;
    private  List<String> commands;

    @Override
    public Robot placeRobot(Position position) throws CollisionException, EdgeDetectedException {

        // Create the Table if it doesnt exist
        table = (RobotTable) RobotTable.getInstance(tableLength, tableBreadth);
        ConfigUtil.setDirectionMap(directionMap);


        RobotPosition robotPosition = (RobotPosition) position;
        // Create a new robot
        ToyRobot robot = ToyRobot.builder().id(getNextAvailableId()).build();


        // If first robot then set ACTIVE
        if (table.getRobots().isEmpty())
            table.setActiveRobotId(robot.getId());


        // Validate if the position is not a collision

        validateMove(robotPosition);

        robot.setPosition(robotPosition);
        table.getRobots().add(robot);


        return robot;
    }


    @Override
    public ToyRobot activateRobot(int id) throws TableInitializationException, ResourceNotFoundException {

        validateTable();

        // Get the robot
        Optional<ToyRobot> newActiveRobot = getRobotById(id);

        if(newActiveRobot.isPresent())
        {
            table.setActiveRobotId(id);
            return newActiveRobot.get();
        }

        else
            throw new ResourceNotFoundException("Robot Not Found");
    }


    @Override
    public ToyRobot rotateRobot(Rotation rotationDirection) throws TableInitializationException, ResourceNotFoundException {

        validateTable();

            // Get the ACTIVE robot
        Optional<ToyRobot> robot = getRobotById(table.getActiveRobotId());

        if (!robot.isEmpty()) {

            robot.get().turn(table, rotationDirection);

            return robot.get();
        } else
            throw new ResourceNotFoundException("Robot Not Found");

    }

    @Override
    public ToyRobot moveRobot() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {

        validateTable();

        RobotTable robotTable = table;
        Optional<ToyRobot> robot = getRobotById(robotTable.getActiveRobotId());

        if(robot.isPresent())
        {
            RobotPosition position =  robot.get().getPosition();
            // Get the new Position
            RobotPosition newPosition = position.getNextPosition(position.getDirection());

            // Check if there is a collision / edge exception
            validateMove(newPosition);

            return (ToyRobot) robot.get().move(robotTable);
        }
        else
            throw new ResourceNotFoundException("Robot Not Found");


    }

    @Override
    public Table report() throws TableInitializationException {
        validateTable();
        return table;
    }

    @Override
    public int getNextAvailableId()
    {
        int maxId =  table.getRobots()
                .stream().mapToInt(ToyRobot::getId).max().orElse(0);
        return maxId + 1;
    }

    @Override
    public Optional<ToyRobot> getRobotById(int id)
    {
        // Get the Active Robot
        return table.getRobots()
                .stream()
                .filter(r -> r.getId() == table.getActiveRobotId())
                .findFirst();
    }

    @Override
    public void validateTable() throws TableInitializationException {

        // Check if Table has been initialized
        if(Objects.isNull(table))
            throw new TableInitializationException("Table not initialized - Use the PLACE command first");
    }


    @Override
    public void validateMove(RobotPosition newPosition) throws CollisionException, EdgeDetectedException {

        if(!table.isOnTable(newPosition.getX(), newPosition.getY()))
            throw new EdgeDetectedException("Edge Detected - Invalid Move");

        if(table.detectCollision(newPosition.getX(), newPosition.getY()))
            throw new CollisionException("Collision Detected");
    }


}
