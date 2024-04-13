package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");


    @Override
    public void execute() throws InterruptOperationException {

            ConsoleHelper.writeMessage(res.getString("before"));

            String code = ConsoleHelper.askCurrencyCode();
            CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);

            while (true){
                try {
                    ConsoleHelper.writeMessage(res.getString("specify.amount"));
                    String s = ConsoleHelper.readString();
                    if (s != null) {
                        int i = -1;

                        try {
                            i = Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            ConsoleHelper.writeMessage(res.getString("specify.amount"));
                            continue;
                        }
                        if (i < 0){
                            ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                            continue;
                        }

                        if (!currencyManipulator.isAmountAvailable(i)) {
                            ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                            continue;
                        }
                        Map<Integer, Integer> map = currencyManipulator.withdrawAmount(i);
                        for (Map.Entry<Integer, Integer> val : map.entrySet()) {
                            ConsoleHelper.writeMessage(val.getKey() + " - " + val.getValue());
                        }
                        ConsoleHelper.writeMessage(String.format(res.getString("success.format") , i, code));
                        break;
                    }
                } catch (NumberFormatException e){
                    continue;
                } catch (NotEnoughMoneyException e) {
                    ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                    continue;
                }
            }



    }
}
