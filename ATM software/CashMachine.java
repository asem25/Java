package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        Operation operation = null;
        boolean LoginFlag = false;
        try {
            Operation operation1 = Operation.LOGIN;
            CommandExecutor.execute(operation1);
            do {

                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);

            }while (operation != Operation.EXIT);
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }

    }
}
