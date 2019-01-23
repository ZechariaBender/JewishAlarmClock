package com.zevnzac.jewishalarmclock;


import java.util.ArrayList;

public class AlarmList {

    private static AlarmList alarmList;
    private ArrayList<AlarmObject> list;

    public static synchronized AlarmList get() {
        if (alarmList == null) alarmList = new AlarmList();
        return alarmList;
    }

    public void insertAlarm(AlarmObject alarm) {
        list.add(alarm);
    }

    public ArrayList<AlarmObject> getList() {
        return list;
    }

    public void setList(ArrayList<AlarmObject> list) {
        this.list = list;
    }

//    public ArrayList<String> getStringList() {
//        ArrayList<String> strings = new ArrayList<>();
//        for (AlarmObject alarm: getList())
//            strings.add(alarm.toString());
//        return strings;
//    }
}
