package com.nab.toyrobot.controller;


import com.nab.toyrobot.enums.Direction;
import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.*;
import com.nab.toyrobot.request.RequestDto;
import com.nab.toyrobot.service.TableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game/api")
public class GameApiController {

    @Autowired
    public TableService tableService;



    /************************************************************************************************
     * http://localhost:8080/game/place
     *
     * @param
     * @return Robot
     * @throws
     */
    @PostMapping("/command/place")
    public ToyRobot placeRobot(@Valid @RequestBody RequestDto requestDto) throws CollisionException, EdgeDetectedException {
        return (ToyRobot) tableService.placeRobot(RobotPosition.builder()
                .x(requestDto.getX())
                .y(requestDto.getY())
                .direction(requestDto.getDirection())
                .build());

    }
    /************************************************************************************************
     * http://localhost:8080/game/place
     *
     * @param
     * @return Robot
     * @throws
     */
    @PostMapping("/place/command")
    public ToyRobot placeRobotCommand(@RequestBody String command) throws CollisionException, EdgeDetectedException {

        String[] tokens = command.split(" ");
        return (ToyRobot) tableService.placeRobot(RobotPosition.builder()
                .x(Integer.parseInt(tokens[1]))
                .y(Integer.parseInt(tokens[2]))
                .direction(tokens[3])
                .build());

    }


    /************************************************************************************************
     * http://localhost:8080/game/rotate
     *
     * @param
     * @return Robot
     * @throws
     */
    @PutMapping("/rotate/{direction}")
    public ToyRobot rotateRobot(@Valid @RequestBody RequestDto requestDto) throws TableInitializationException, ResourceNotFoundException {
        return tableService.rotateRobot(Rotation.valueOf(requestDto.getCommand()));
    }

    /************************************************************************************************
     * http://localhost:8080/game/move
     *
     * @param
     * @return Robot
     * @throws
     */
    @PutMapping("/move")
    public ToyRobot moveRobot(@Valid @RequestBody RequestDto requestDto) throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException {
        return tableService.moveRobot();
    }


    /************************************************************************************************
     * http://localhost:8080/game/report
     *
     * @param
     * @return Set<Robot>
     * @throws
     */
    @GetMapping("/report")
    public Table report() throws TableInitializationException {
        return tableService.report();
    }


    /************************************************************************************************
     * http://localhost:8080/game/activate/{id}
     *
     * @param
     * @return Robot>
     * @throws
     */
    @GetMapping("/activate")
    public Robot activateRobot(@PathVariable String id) throws TableInitializationException, ResourceNotFoundException {
        return tableService.activateRobot(Integer.parseInt(id));
    }

}
