package com.nab.toyrobot.controller;

import com.nab.toyrobot.enums.Direction;
import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.RobotPosition;
import com.nab.toyrobot.model.Rotation;
import com.nab.toyrobot.model.ToyRobot;
import com.nab.toyrobot.request.RequestDto;
import com.nab.toyrobot.service.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private TableService tableService;

    @InjectMocks
    private GameApiController gameController;

    @Test
    public void testPlaceRobot() throws CollisionException, EdgeDetectedException {
        // setup test data
        RequestDto requestDto = RequestDto.builder().x(0).y(0).direction(Direction.NORTH).build();

        RobotPosition robotPosition = RobotPosition.builder()
                .x(requestDto.getX())
                .y(requestDto.getY())
                .direction(requestDto.getDirection())
                .build();

        ToyRobot toyRobot = ToyRobot.builder().position(robotPosition).build();

        // mock the service method
        when(tableService.placeRobot(robotPosition)).thenReturn(toyRobot);

        // call the controller method
        ToyRobot result = gameController.placeRobot(requestDto);

        // assert the result
        assertEquals(toyRobot, result);

        // verify that the service method was called with the correct argument
        verify(tableService, times(1)).placeRobot(robotPosition);
    }

    @Test
    public void testPlaceRobotThenReturnCollisionException() throws CollisionException, EdgeDetectedException {
        // given
        RequestDto requestDto = RequestDto.builder().x(0).y(0).direction(Direction.NORTH).build();
        when(tableService.placeRobot(any(RobotPosition.class))).thenThrow(new CollisionException("COLLISION DETECTED"));
        assertThrows(CollisionException.class, () -> gameController.placeRobot(requestDto));
    }

    @Test
    public void testPlaceRobotThenReturnEdgeException() throws CollisionException, EdgeDetectedException {
        // given
        RequestDto requestDto = RequestDto.builder().x(0).y(0).direction(Direction.NORTH).build();
        when(tableService.placeRobot(any(RobotPosition.class))).thenThrow(new EdgeDetectedException("Edge Detected"));
        assertThrows(EdgeDetectedException.class, () -> gameController.placeRobot(requestDto));
    }

    @Test
    public void testMoveRobot() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // setup test data
        RequestDto requestDto = RequestDto.builder().x(0).y(0).direction(Direction.NORTH).build();

        RobotPosition robotPosition = RobotPosition.builder()
                .x(requestDto.getX())
                .y(requestDto.getY())
                .direction(requestDto.getDirection())
                .build();

        RobotPosition newRobotPosition = RobotPosition.builder()
                .x(requestDto.getX())
                .y(requestDto.getY()+1)
                .direction(requestDto.getDirection())
                .build();

        ToyRobot toyRobot = ToyRobot.builder().position(robotPosition).build();
        ToyRobot newToyRobot = ToyRobot.builder().position(newRobotPosition).build();

        // mock the service method
        when(tableService.moveRobot()).thenReturn(newToyRobot);

        // call the controller method
        ToyRobot result = gameController.moveRobot(requestDto);

        // assert the result
        assertEquals(newToyRobot, result);

        // verify that the service method was called with the correct argument
        verify(tableService, times(1)).moveRobot();
    }

    @Test
    public void testRotateRobot() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        // setup test data
        RequestDto requestDto = RequestDto.builder().command("LEFT").x(0).y(0).direction(Direction.NORTH).build();
        Rotation rotationDirection = Rotation.LEFT;

        RobotPosition robotPosition = RobotPosition.builder()
                .x(requestDto.getX())
                .y(requestDto.getY())
                .direction(requestDto.getDirection())
                .build();

        RobotPosition newRobotPosition = RobotPosition.builder()
                .x(requestDto.getX())
                .y(requestDto.getY())
                .direction(Direction.WEST)
                .build();

        ToyRobot toyRobot = ToyRobot.builder().id(1).position(robotPosition).build();
        ToyRobot newToyRobot = ToyRobot.builder().id(1).position(newRobotPosition).build();

        // mock the service method
        when(tableService.rotateRobot(any())).thenReturn(newToyRobot);
        //when(Rotation.valueOf(any())).thenReturn(Rotation.LEFT);

        // call the controller method
        ToyRobot result = gameController.rotateRobot(requestDto);

        // assert the result
        assertEquals(newToyRobot, result);

        // verify that the service method was called with the correct argument
        verify(tableService, times(1)).rotateRobot(rotationDirection);
    }

}