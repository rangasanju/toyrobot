package com.nab.toyrobot.commands;

import com.nab.toyrobot.exception.CollisionException;
import com.nab.toyrobot.exception.EdgeDetectedException;
import com.nab.toyrobot.exception.ResourceNotFoundException;
import com.nab.toyrobot.exception.TableInitializationException;
import com.nab.toyrobot.model.Table;
import com.nab.toyrobot.response.ResponseDto;
import com.nab.toyrobot.service.TableService;

import java.util.ArrayList;
import java.util.List;

public interface Command {

    public ResponseDto execute(String[] tokens, Table table, TableService tableService) throws CollisionException, EdgeDetectedException, TableInitializationException, ResourceNotFoundException;


}
