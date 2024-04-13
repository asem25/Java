package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");
    public static void writeMessage(String message){
        try {
            System.out.println(message);
        }catch (Exception ignored){

        }
    }
    public static String readString() throws InterruptOperationException{
        try {
            String fromcons = bis.readLine();
            if (fromcons.equalsIgnoreCase(res.getString("operation.EXIT"))){
                throw new InterruptOperationException();
            }
            return fromcons;
        } catch (IOException ignored) {
            return null;
        }
    }
    public static String askCurrencyCode() throws  InterruptOperationException{
        while (true) {
            writeMessage(res.getString("choose.currency.code"));
            try {
                String currencycode = bis.readLine();
                if (currencycode.length() != 3){
                    writeMessage(res.getString("invalid.data"));
                }else {
                    return currencycode.toUpperCase();
                }
            } catch (IOException ignored) {

            }
        }
    }
    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException{
        String[] tores = new String[2];

        while (true) {
            writeMessage(res.getString("choose.denomination.and.count.format"));
            String[] strings = null;
            try {
                strings = readString().split(" ");

                if (strings == null || strings.length != 2){
                    writeMessage(res.getString("invalid.data"));
                    continue;
                }

                int i1 = Integer.parseInt(strings[0]);
                int i2 = Integer.parseInt(strings[1]);

                if (i1 <= 0 || i2 <= 0) {
                    writeMessage(res.getString("invalid.data"));
                    continue;
                }

                tores[0] = strings[0];
                tores[1] = strings[1];
                break;
            } catch (Exception e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
        }
        return tores;
    }
    public static Operation askOperation()throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("choose.operation"));
        ConsoleHelper.writeMessage("\t 1 - " + res.getString("operation.INFO"));
        ConsoleHelper.writeMessage("\t 2 - " + res.getString("operation.DEPOSIT"));
        ConsoleHelper.writeMessage("\t 3 - " + res.getString("operation.WITHDRAW"));
        ConsoleHelper.writeMessage("\t 4 - " + res.getString("operation.EXIT"));
        while (true){
            try {
                Integer i = Integer.parseInt(ConsoleHelper.readString());
                return Operation.getAllowableOperationByOrdinal(i);
            } catch (Exception e) {
                ConsoleHelper.writeMessage(res.getString("the.end"));
                continue;
            }
        }
    }
    public static void printExitMessage(){
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }
}
