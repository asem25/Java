package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    Path logDir;
    ArrayList<Log> logList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.y H:m:s");
    public LogParser(Path logDir) {
        this.logDir = logDir;
        readLog();
    }

    private void readLog(){
        try(DirectoryStream<Path> files = Files.newDirectoryStream(logDir))
        {
            for (Path file : files) {
                if (file.toString().toLowerCase().endsWith(".log")){
                    List<String> list = Files.readAllLines(file);
                    for (String string : list) {
                        {
                            String[] params = string.split("\t");

                            try {
                                Log log = new Log(params[0], params[1], simpleDateFormat.parse(params[2]), readEvent(params[3]),null, null);
                                log.setStatus(params[4]);
                                if (log.getEvent() == Event.SOLVE_TASK || log.getEvent() == Event.DONE_TASK) {

                                    log.setParam(readAdditionalParameter(params[3]));
                                }
                                logList.add(log);

                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logList.sort((o1, o2) -> o1.date.compareTo(o2.date));
    }
    private Event readEvent(String s){
        Event event = null;
        if (s.contains("LOGIN")){
            event = Event.LOGIN;
        }else if(s.contains("DOWNLOAD_PLUGIN")){
            event = Event.DOWNLOAD_PLUGIN;
        }else if(s.contains("WRITE_MESSAGE")){
            event = Event.WRITE_MESSAGE;
        }else if(s.contains("SOLVE_TASK")){
            event = Event.SOLVE_TASK;
        }else if(s.contains("DONE_TASK")){
            event = Event.DONE_TASK;
        }
        return event;
    }
    private int readAdditionalParameter(String lineToParse) {
        if (lineToParse.contains("SOLVE_TASK")) {
            lineToParse = lineToParse.replace("SOLVE_TASK", "").replaceAll(" ", "");
            return Integer.parseInt(lineToParse);
        } else {
            lineToParse = lineToParse.replace("DONE_TASK", "").replaceAll(" ", "");
            return Integer.parseInt(lineToParse);
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            FilterDate(after, before, set, log, 0);
        }
        return set;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getName().equals(user)){
                FilterDate(after, before, set, log, 0);
            }
        }

        return set;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == event){
                FilterDate(after, before, set, log, 0);
            }
        }

        return set;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getStatus() == status){
                FilterDate(after, before, set, log, 0);
            }
        }
        return set;

    }
    private  void FilterDate(Date after, Date before, Set<String> set, Log log, int settingsforadd){
        //The method allows you to add the data you need from log to set in the period from after to before.
        // To save the ip, pass 0 as the last parameter, for user 1, for event 2, for date 3

        if (after != null && before != null){
            if (log.getDate().after(after) && log.getDate().before(before)){
                if(settingsforadd == 0) {
                    set.add(log.getIp());
                }else if(settingsforadd == 1){
                    set.add(log.getName());
                }else if(settingsforadd == 2){
                    set.add(log.getEvent().toString());
                }else if(settingsforadd == 3){
                    set.add(log.getDate().toString());
                }

            }
        }else if(after != null){
            if (log.getDate().after(after)){
                if(settingsforadd == 0) {
                    set.add(log.getIp());
                }else if(settingsforadd == 1){
                    set.add(log.getName());
                }else if(settingsforadd == 2){
                    set.add(log.getEvent().toString());
                }else if(settingsforadd == 3){
                    set.add(log.getDate().toString());
                }
            }
        }else if(before != null){
            if (log.getDate().before(before)){
                if(settingsforadd == 0) {
                    set.add(log.getIp());
                }else if(settingsforadd == 1){
                    set.add(log.getName());
                }else if(settingsforadd == 2){
                    set.add(log.getEvent().toString());
                }else if(settingsforadd == 3){
                    set.add(log.getDate().toString());
                }
            }
        }else {
            if(settingsforadd == 0) {
                set.add(log.getIp());
            }else if(settingsforadd == 1){
                set.add(log.getName());
            }else if(settingsforadd == 2){
                set.add(log.getEvent().toString());
            }else if(settingsforadd == 3){
                set.add(log.getDate().toString());
            }
        }
    }
    private  void FilterDateSetDate(Date after, Date before, Set<Date> set, Log log){
        //The method allows you to add the data you need from log to set in the period from after to before.
        if (after != null && before != null){
            if (log.getDate().after(after) && log.getDate().before(before)){
                    set.add(log.getDate());

            }
        }else if(after != null){
            if (log.getDate().after(after)){
                    set.add(log.getDate());

            }
        }else if(before != null){
            if (log.getDate().before(before)){
                    set.add(log.getDate());

            }
        }else {
            set.add(log.getDate());
        }
    }
    private  void FilterDateSetEvent(Date after, Date before, Set<Event> set, Log log){
        //The method allows you to add the data you need from log to set in the period from after to before.
        if (after != null && before != null){
            if (log.getDate().after(after) && log.getDate().before(before)){
                set.add(log.getEvent());

            }
        }else if(after != null){
            if (log.getDate().after(after)){
                set.add(log.getEvent());

            }
        }else if(before != null){
            if (log.getDate().before(before)){
                set.add(log.getEvent());

            }
        }else {
            set.add(log.getEvent());
        }
    }
    private boolean betweenDate(Date after, Date before, Log log){
        // true if after < log.getDate() < before
        if (after != null && before != null){
            return log.getDate().after(after) && log.getDate().before(before);
        }else if(after != null){
            return log.getDate().after(after);
        }else if(before != null){
            return log.getDate().before(before);
        }else {
            return  true;
        }
    }

    @Override
    public String toString() {
        return "LogParser{" +
                "logList=" + logList +
                '}';
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            set.add(log.getName());
        }
        return set;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            FilterDate(after, before, set, log, 1);
        }

        return set.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getName().equals(user)){
                FilterDate(after, before, set, log, 2);
            }
        }

        return set.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getIp().equals(ip)){
                FilterDate(after, before, set, log, 1);
            }
        }
        return set;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == Event.LOGIN){
                FilterDate(after, before, set, log, 1);
            }
        }

        return set;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == Event.DOWNLOAD_PLUGIN){
                FilterDate(after, before, set, log, 1);
            }
        }

        return set;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == Event.WRITE_MESSAGE){
                FilterDate(after, before, set, log, 1);
            }
        }

        return set;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == Event.SOLVE_TASK){
                FilterDate(after, before, set, log, 1);
            }
        }

        return set;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == Event.SOLVE_TASK){
                if (log.getParam() == task){
                    FilterDate(after, before, set, log, 1);
                }
            }
        }
        return set;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String> set = new HashSet<>();

        for (Log log : logList) {
            if (log.getEvent() == Event.DONE_TASK){
                FilterDate(after, before, set, log, 1);
            }
        }

        return set;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getEvent() == Event.DONE_TASK){
                if (log.getParam() == task){
                    FilterDate(after, before, set, log, 1);
                }
            }
        }
        return set;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> sets = new HashSet<>();
        for (Log log : logList) {
            if (log.getName().equals(user)){
                if (log.getEvent() == event){
                    FilterDateSetDate(after, before, sets, log);
                }
            }
        }
        return sets;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set<Date> sets = new HashSet<>();
        for (Log log : logList) {
                if (log.getStatus() == Status.FAILED){
                    FilterDateSetDate(after, before, sets, log);
                }
        }
        return sets;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set<Date> sets = new HashSet<>();
        for (Log log : logList) {
            if (log.getStatus() == Status.ERROR){
                FilterDateSetDate(after, before, sets, log);
            }

        }
        return sets;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {

        HashSet<Date> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getName().equals(user)){
                if (log.getEvent() == Event.LOGIN) {
                    FilterDateSetDate(after, before, set, log);
                    for (Date date : set) {
                        return date;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        HashSet<Date> set = new HashSet<>();
        for (Log log : logList) {
            if(log.getName().equals(user)){
                if(log.getEvent() == Event.SOLVE_TASK){
                    if (log.getParam() == task) {
                        FilterDateSetDate(after, before, set, log);
                        for (Date date : set) {
                            return date;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        HashSet<Date> set = new HashSet<>();
        for (Log log : logList) {
            if(log.getName().equals(user)){
                if (log.getEvent() == Event.DONE_TASK){
                    if (log.getParam() == task){
                        FilterDateSetDate(after, before, set, log);
                        for (Date date : set) {
                            return date;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set<Date> sets = new HashSet<>();
        for (Log log : logList) {
            if(log.getName().equals(user)){
                if(log.getEvent() == Event.WRITE_MESSAGE){
                    FilterDateSetDate(after, before, sets, log);
                }
            }
        }
        return sets;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set<Date> sets = new HashSet<>();
        for (Log log : logList) {
            if(log.getName().equals(user)){
                if(log.getEvent() == Event.DOWNLOAD_PLUGIN){
                    FilterDateSetDate(after, before, sets, log);

                }
            }
        }
        return sets;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for (Log log : logList) {
            FilterDateSetEvent(after, before, set, log);
        }
        return set;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getIp().equals(ip)){
                FilterDateSetEvent(after, before, set, log);
            }
        }
        return set;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getName().equals(user)){
                FilterDateSetEvent(after, before, set, log);
            }
        }
        return set;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getStatus() == Status.FAILED){
                FilterDateSetEvent(after, before, set, log);
            }
        }
        return set;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> set = new HashSet<>();
        for (Log log : logList) {
            if (log.getStatus() == Status.ERROR){
                FilterDateSetEvent(after, before, set, log);
            }
        }
        return set;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        int count = 0;
        for (Log log : logList) {
            if (betweenDate(after, before, log)) {
                if (log.getEvent() == Event.SOLVE_TASK) {
                    if (log.getParam() == task)
                        count++;
                }
            }
        
        }
        return count;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        int count = 0;
        for (Log log : logList) {
            if (betweenDate(after, before, log)) {
                if (log.getEvent() == Event.DONE_TASK) {
                    if (log.getParam() == task)
                        count++;
                }
            }

        }
        return count;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Log log : logList) {
            if (betweenDate(after, before, log)){
                if (log.getEvent() == Event.SOLVE_TASK){
                    if (!map.containsKey(log.getParam())){
                        map.put(log.getParam(), 1);
                    }else {
                        map.put(log.getParam(), map.get(log.getParam()) + 1);
                    }
                }
            }
        }
        
        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();

        for (Log log : logList) {
            if (betweenDate(after, before, log)){
                if (log.getEvent() == Event.DONE_TASK){
                    if (!map.containsKey(log.getParam())){
                        map.put(log.getParam(), 1);
                    }else {
                        map.put(log.getParam(), map.get(log.getParam()) + 1);
                    }
                }
            }
        }

        return map;
    }

    @Override
    public Set<Object> execute(String query) {

        switch (query){
            case "get ip":
                return logList.stream().map(Log::getIp).collect(Collectors.toSet());
            case "get user":
                return logList.stream().map(Log::getName).collect(Collectors.toSet());
            case "get date":
                return logList.stream().map(Log::getDate).collect(Collectors.toSet());
            case "get event":
                return logList.stream().map(Log::getEvent).collect(Collectors.toSet());
            case "get status":
                return logList.stream().map(Log::getStatus).collect(Collectors.toSet());
            default:
                Set<Object> set = new HashSet<>();
                try {
                    Pattern pattern = Pattern.compile("get (?<field1>\\w+) for (?<field2>\\w+) = \"(?<value1>.*?)\"");
                    Pattern pattern1  =  Pattern.compile("and date between \"(?<date1>.+?)\" and \"(?<date2>.+?)\"");
                    Matcher matcher = pattern.matcher(query);
                    matcher.find();
                    Matcher matcher2 = pattern1.matcher(query);
                    String search = matcher.group(1);
                    String key = matcher.group(2);
                    String value = matcher.group(3);
                    String date1 = null;
                    String date2 = null;
                    boolean flag = false;
                    if (matcher2.find()){
                        flag = true;
                        date1 = matcher2.group(1);
                        date2 = matcher2.group(2);
                    }

                    for (Log log : logList) {
                        Object o = Log.class.getDeclaredField(key).get(log);
                        if (o instanceof Date && (((Date) o).getTime() == simpleDateFormat.parse(value).getTime())
                                || (o instanceof Event && (Event) o == Event.valueOf(value))
                                || (o instanceof Status && (Status) o == Status.valueOf(value))
                                || (o instanceof String && o.toString().equalsIgnoreCase(value))) {
                            if (flag) {
                                if (betweenDate(simpleDateFormat.parse(date1), simpleDateFormat.parse(date2), log)){
                                    set.add(Log.class.getDeclaredField(search).get(log));
                                }
                            }else {
                                set.add(Log.class.getDeclaredField(search).get(log));
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
                return set;
        }


    }

    class Log {
        public String user;
        public String ip;
        public Date date;
        public Event event;
        public Status status;
        public Integer Param;


        public Log( String ip,String name, Date date, Event event, Status status, Integer param) {
            this.user = name;
            this.ip = ip;
            this.date = date;
            this.event = event;
            this.status = status;
            this.Param = param;
        }

        public Event getEvent() {
            return event;
        }

        public void setStatus(String status) {
            if (status.contains("OK")){
                this.status = Status.OK;
            }else if(status.contains("FAILED")){
                this.status = Status.FAILED;
            }else if(status.contains("ERROR")){
                this.status = Status.ERROR;
            }
        }
        public void setParam(Integer param) {
            Param = param;
        }

        public String getIp() {
            return ip;
        }

        public String getName() {
            return user;
        }

        @Override
        public String toString() {
            return "Log{" +
                    "name='" + user + '\'' +
                    ", ip='" + ip + '\'' +
                    ", date=" + date +
                    ", event=" + event +
                    ", status='" + status + '\'' +
                    ", Param=" + Param +
                    '}';
        }

        public Date getDate() {
            return date;
        }

        public Status getStatus() {
            return status;
        }

        public Integer getParam() {
            return Param;
        }
    }

}