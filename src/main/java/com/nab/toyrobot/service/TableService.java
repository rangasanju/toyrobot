package com.nab.toyrobot.service;

import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.*;

import java.util.Optional;

public interface TableService {

    public Robot placeRobot(Position position) throws CollisionException, EdgeDetectedException;

    public Optional<ToyRobot> getRobotById(int id);

    public int getNextAvailableId();

    public ToyRobot moveRobot() throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException;

    public ToyRobot rotateRobot(Rotation rotationDirection) throws TableInitializationException, ResourceNotFoundException;

    public Table report() throws TableInitializationException;

    public void validateTable() throws TableInitializationException;

    public void validateMove(RobotPosition newPosition) throws CollisionException, EdgeDetectedException;
    public ToyRobot activateRobot(int id) throws TableInitializationException, ResourceNotFoundException;

}
