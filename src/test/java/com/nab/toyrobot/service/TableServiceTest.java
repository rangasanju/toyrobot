package com.nab.toyrobot.service;


import com.nab.toyrobot.enums.Direction;
import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.*;
import com.nab.toyrobot.request.RequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {

    @Mock
    private RobotTable robotTable;

    @InjectMocks
    private TableServiceImpl tableService;


    @Test
    public void testPlaceRobot_successful() throws CollisionException, EdgeDetectedException {
        // given

        RequestDto requestDto = RequestDto.builder().x(1).y(2).direction(Direction.NORTH).build();
        RobotPosition robotPosition = RobotPosition.builder()
                .x(1)
                .y(2)
                .direction(Direction.NORTH)
                .build();

        // when
        ToyRobot result = (ToyRobot) tableService.placeRobot(robotPosition);

        // then
        assertEquals(robotPosition.getX(), result.getPosition().getX());
        assertEquals(robotPosition.getY(), result.getPosition().getY());
        assertEquals(robotPosition.getDirection(), result.getPosition().getDirection());
    }

    @Test
    public void testPlaceRobot_collisionException() throws CollisionException, EdgeDetectedException {
        // given

        RobotTable robotTable1 = (RobotTable) RobotTable.getInstance(5, 5);
        RobotPosition robotPosition = RobotPosition.builder()
                .x(4)
                .y(4)
                .direction(Direction.NORTH)
                .build();

        // Place the robot already at same position
        tableService.placeRobot(robotPosition);

        // then
        assertThrows(CollisionException.class, () -> tableService.placeRobot(robotPosition));
    }


    @Test
    public void testPlaceRobot_EdgeException() {
        // given

        RobotTable robotTable1 = (RobotTable) RobotTable.getInstance(5, 5);
        RobotPosition robotPosition = RobotPosition.builder()
                .x(6)
                .y(6)
                .direction(Direction.NORTH)
                .build();

        // then
        assertThrows(EdgeDetectedException.class, () -> tableService.placeRobot(robotPosition));
    }

    @Test
    public void testMoveRobot_successful() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // given

        RobotPosition robotPosition = RobotPosition.builder()
                .x(3)
                .y(3)
                .direction(Direction.NORTH)
                .build();

        Robot robot = tableService.placeRobot(robotPosition);
        tableService.activateRobot(((ToyRobot) robot).getId());
        // when
        ToyRobot result = (ToyRobot) tableService.moveRobot();

        // then
        assertEquals(robotPosition.getX(), result.getPosition().getX());
        assertEquals(robotPosition.getY() + 1, result.getPosition().getY());
        assertEquals(robotPosition.getDirection(), result.getPosition().getDirection());
    }

    @Test
    public void testMoveRobot_ReturnsCollisionException() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // given

        RobotTable robotTable1 = (RobotTable) RobotTable.getInstance(5, 5);
        RobotPosition robotPosition1 = RobotPosition.builder()
                .x(2)
                .y(2)
                .direction(Direction.WEST)
                .build();

        RobotPosition robotPosition2 = RobotPosition.builder()
                .x(3)
                .y(2)
                .direction(Direction.WEST)
                .build();

        // Place 2 robots
        Robot robot1 = tableService.placeRobot(robotPosition1);
        tableService.activateRobot(((ToyRobot) robot1).getId());
        tableService.placeRobot(robotPosition2);

        // then
        assertThrows(CollisionException.class, () -> tableService.moveRobot());
    }

    @Test
    public void testMoveRobot_ReturnsEdgeException() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // given

        RobotTable robotTable1 = (RobotTable) RobotTable.getInstance(5, 5);
        RobotPosition robotPosition1 = RobotPosition.builder()
                .x(0)
                .y(0)
                .direction(Direction.SOUTH)
                .build();

        // Place 2 robots
        Robot robot1 = tableService.placeRobot(robotPosition1);
        tableService.activateRobot(((ToyRobot) robot1).getId());


        // then
        assertThrows(EdgeDetectedException.class, () -> tableService.moveRobot());
    }


    @Test
    public void testRotateRobot_successful() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // given
        RobotTable robotTable1 = (RobotTable) RobotTable.getInstance(5, 5);
        RobotPosition robotPosition1 = RobotPosition.builder()
                .x(3)
                .y(3)
                .direction(Direction.NORTH)
                .build();

        // Place 2 robots
        ToyRobot robot = (ToyRobot) tableService.placeRobot(robotPosition1);
        tableService.activateRobot(((ToyRobot) robot).getId());


        tableService.rotateRobot(Rotation.LEFT);

        // then

        assertEquals(robot.getPosition().getDirection(), Direction.WEST);
    }


    @Test
    public void testGetRobotById_successful() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // given
        RobotTable robotTable1 = (RobotTable) RobotTable.getInstance(5, 5);

        RobotPosition robotPosition1 = RobotPosition.builder()
                .x(0)
                .y(3)
                .direction(Direction.NORTH)
                .build();

        ToyRobot robot1 = (ToyRobot) tableService.placeRobot(robotPosition1);
        tableService.activateRobot(((ToyRobot) robot1).getId());

        Optional<ToyRobot> robot2 = tableService.getRobotById(robot1.getId());

        assertEquals(robot1.getId(), robot2.get().getId());
    }



}
