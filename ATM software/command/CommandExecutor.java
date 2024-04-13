package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.Operation;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = update();
    private static Map<Operation, Command> update(){
        Map<Operation, Command> map = new HashMap<>();
        for (Operation value : Operation.values()) {
            switch (value){
                case LOGIN:
                    map.put(Operation.LOGIN, new LoginCommand());
                    break;
                case EXIT:
                    map.put(Operation.EXIT, new ExitCommand());
                    break;
                case INFO:
                    map.put(Operation.INFO, new InfoCommand());
                    break;
                case DEPOSIT:
                    map.put(Operation.DEPOSIT, new DepositCommand());
                    break;
                case WITHDRAW:
                    map.put(Operation.WITHDRAW, new WithdrawCommand());
                    break;
            }
        }
        return map;
    }

    private CommandExecutor() {
    }

    public static final void execute(Operation operation) throws InterruptOperationException {
        allKnownCommandsMap.get(operation).execute();
    }
}
