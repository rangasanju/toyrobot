package com.nab.toyrobot.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nab.toyrobot.model.Table;
import com.nab.toyrobot.model.ToyRobot;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ToyRobot robot;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Table table;
}
