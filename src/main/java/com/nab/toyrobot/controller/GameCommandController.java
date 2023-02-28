package com.nab.toyrobot.controller;

import com.nab.toyrobot.commands.Command;
import com.nab.toyrobot.commands.Place;
import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.Table;
import com.nab.toyrobot.model.ToyRobot;
import com.nab.toyrobot.response.ResponseDto;
import com.nab.toyrobot.service.TableService;
import com.nab.toyrobot.service.TableServiceImpl;
import com.nab.toyrobot.util.ConfigUtil;
import com.nab.toyrobot.validator.ValidateCommand;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/game/command")

public class GameCommandController {

    @Autowired
    public TableService tableService;
    @Value("#{${robot.commands}}")
    private Map<String, Integer> commands;
    @Value("#{${robot.rotation}}")
    private Map<String, Integer> rotation;
    @Value("#{${robot.directions}}")
    private Map<String, List<Integer>> directionMap;

    private Map<String, Command> strategies = new HashMap<>();

    @PostConstruct
    public void init() {
        // Initialize all the configurable values
        ConfigUtil.setDirectionMap(directionMap);
        ConfigUtil.setCommands(commands);
        ConfigUtil.setRotation(rotation);



    }


    /************************************************************************************************
     * http://localhost:8080/game/command
     *
     * @param
     * @return Robot
     * @throws
     */
    @PostMapping("")
    public ResponseDto placeRobot(@RequestParam @ValidateCommand String command) throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String[] tokens = command.split(" ");

        ToyRobot robot = null;
        Table table = null;


        Class<?> clazz = TableServiceImpl.class;

        //Method[] methods = clazz.getDeclaredMethods();
        // Get the method with the given name
        Method method = clazz.getMethod(tokens[0].toLowerCase());

        // Invoke the method on an instance of the class (assuming it's not static)
        Object result = method.invoke(tableService);


        String[] directions = ConfigUtil.getDirectionMap().keySet().toArray(new String[0]);

        for(String dir : directions)
        {
            if(dir.equalsIgnoreCase(tokens[0]))
            {
                strategies.put("PLACE", new Place());
            }
        }
        strategies.get("PLACE").execute(tokens, table, tableService);






//        switch(cmd)
//        {
//            case "PLACE" :
//
//                robot =  (ToyRobot) tableService.placeRobot(RobotPosition
//                        .builder()
//                        .x(Integer.parseInt(tokens[1]))
//                        .y(Integer.parseInt(tokens[2]))
//                        .direction(tokens[3])
//                        .build());
//
//
//                robot =  (ToyRobot) tableService.placeRobot(RobotPosition.builder().x(Integer.parseInt(tokens[1])).y(Integer.parseInt(tokens[2])).direction(tokens[3]).build());
//                break;
//            case "MOVE" :
//                robot =  tableService.moveRobot();
//                break;
//            case "LEFT" :
//                robot = tableService.rotateRobot(Rotation.LEFT);
//                break;
//            case "RIGHT" :
//                robot = tableService.rotateRobot(Rotation.RIGHT);
//                break;
//            case "REPORT" :
//                table =  tableService.report();
//                break;
//        }


        return ResponseDto.builder().robot(robot).table(table).build();

    }

}
