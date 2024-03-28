package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("T:\\idea\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task39\\task3913\\logs"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.yyyy HH:mm:ss");
        String s1 = "13.09.1999 5:04:50";
        String s2 = "05.01.2030 20:22:55";
        try {
            System.out.println(logParser.getFailedEvents(simpleDateFormat.parse(s1),  simpleDateFormat.parse(s2)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(logParser.execute("get user"));
        System.out.println(logParser.execute("get ip"));
        System.out.println(logParser.execute("get date"));
        System.out.println(logParser.execute("get event"));
        System.out.println(logParser.execute("get status"));
        System.out.println(logParser.execute("get user for =date = \"29.02.2028 05:4:07\""));
        System.out.println(logParser.execute("get user for date = \"29.02.2028 05:4:07\""));
        System.out.println(logParser.execute("get date for date = \"25.2.2028 05:4:7\""));

    }
}