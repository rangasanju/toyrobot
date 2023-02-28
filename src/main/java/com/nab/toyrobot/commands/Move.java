package com.nab.toyrobot.commands;

import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.Table;
import com.nab.toyrobot.model.ToyRobot;
import com.nab.toyrobot.response.ResponseDto;
import com.nab.toyrobot.service.TableService;

public class Move implements Command{
    @Override
    public ResponseDto execute(String[] tokens, Table table, TableService tableService) throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        ToyRobot robot =  tableService.moveRobot();
        return ResponseDto.builder().robot(robot).table(table).build();
    }
}
