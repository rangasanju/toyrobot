package com.nab.toyrobot.model;

import com.nab.toyrobot.enums.Direction;
import com.nab.toyrobot.util.ConfigUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class RobotPosition implements Position{


    private int x;
    private int y;
    private String direction;

    @Override
    public RobotPosition getNextPosition(String direction) {
        int nx = x;
        int ny = y;

        // Get the position change for the given direction



        List<Integer> positionChange = ConfigUtil.getDirectionMap().get(direction.toString());

        // Update the position
        nx += positionChange.get(0);
        ny += positionChange.get(1);

        //return new RobotPosition(nx,ny,direction);
        return RobotPosition.builder().x(nx).y(ny).direction(direction).build();
    }

    @Override
    public RobotPosition turn(Rotation rotation) {
        //return new RobotPosition(x, y, direction.nextDirection(Rotation.LEFT));


        // Get the current direction and its index
        String[] directions = ConfigUtil.getDirectionMap().keySet().toArray(new String[0]);
        String currentDirection = "";
        int currentDirectionIndex = 0;

        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(direction.toString()) )
            {
                currentDirection = directions[i];
                currentDirectionIndex = i;
                break;
            }
        }

        // Calculate the new direction index
        int newDirectionIndex;
        if (rotation.equals(Rotation.RIGHT)) {
            newDirectionIndex = (currentDirectionIndex + 1) % directions.length;
        } else {
            newDirectionIndex = (currentDirectionIndex - 1 + directions.length) % directions.length;
        }

        // Get the new direction and its position change
        String newDirection = directions[newDirectionIndex];

        return new RobotPosition(x,y,newDirection);

        //return RobotPosition.builder().x(x).y(y).direction(Direction.valueOf(newDirection)).build();


   }

}
