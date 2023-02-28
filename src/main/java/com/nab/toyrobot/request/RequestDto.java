package com.nab.toyrobot.request;

import com.nab.toyrobot.enums.Direction;
import com.nab.toyrobot.validator.ValidRequestDto;
import lombok.Builder;
import lombok.Data;

@Data
@ValidRequestDto
@Builder
public class RequestDto {
    private String command;
    private Integer x;
    private Integer y;
    private String direction;

}
