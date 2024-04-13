package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command{
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        while (true){
            ConsoleHelper.writeMessage(res.getString("before") + "\n" + res.getString("specify.data"));

            String code = ConsoleHelper.readString();

            String pass = ConsoleHelper.readString();


            if (code == null || pass == null)
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));

            if (code.trim().length() == 12 && pass.trim().length() == 4) {
                if (validCreditCards.containsKey(code) && pass.equals(validCreditCards.getString(code))) {
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), code));
                    break;
                }else {
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), code));
                    ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                }
            }else {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
        }
    }
}
