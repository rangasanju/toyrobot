package com.nab.toyrobot.commands;

import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.model.RobotPosition;
import com.nab.toyrobot.model.Table;
import com.nab.toyrobot.model.ToyRobot;
import com.nab.toyrobot.response.ResponseDto;
import com.nab.toyrobot.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Place implements Command{


    TableService tableService;

    ToyRobot robot = null;
    @Override
    public ResponseDto execute(String[] tokens, Table table, TableService tableService) throws CollisionException, EdgeDetectedException {
        robot =  (ToyRobot) tableService.placeRobot(RobotPosition
                .builder()
                .x(Integer.parseInt(tokens[1]))
                .y(Integer.parseInt(tokens[2]))
                .direction(tokens[3])
                .build());
        return ResponseDto.builder().robot(robot).table(table).build();

    }
}
