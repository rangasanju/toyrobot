package com.nab.toyrobot.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nab.toyrobot.serialize.NameSerializer;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class ToyRobot implements Robot {


    @JsonProperty("id")
    @JsonSerialize(using = NameSerializer.class)
    private int id;

    private RobotPosition position;

    @Override
    public Robot move(Table table) {

        if (!Objects.isNull(position)) {
            RobotPosition newPosition = position.getNextPosition(position.getDirection());
            position = newPosition;
        }
        return this;
    }

    @Override
    public Robot turn(Table table, Rotation rotation) {
        if (!Objects.isNull(position)) {
            position = position.turn(rotation);
        }
        return this;
    }


}
