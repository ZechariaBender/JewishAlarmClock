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

    public ArrayList<String> getStringList() {
        ArrayList<String> strings = new ArrayList<>();
        for (AlarmObject alarm: getList())
            strings.add(alarm.toString());
        return strings;
    }

    //
//    private static AlarmFactory factory;
//
//    private ArrayList<AlarmObject> mAlarmList;
//
//    private AlarmFactory(){
//        loadAlarmList();
//    }
//
//    public static synchronized AlarmFactory getFactory() {
//        if(factory == null) factory = new AlarmFactory();
//        return factory;
//    }
//
//    public AlarmObject createAlarm(int hour, int minute) {
//        AlarmObject alarm = new AlarmObject(hour, minute);
//        mAlarmList.add(alarm);
//        return alarm;
//    }
//
//    public AlarmObject insertAlarm(AlarmObject alarm) {
//        mAlarmList.add(alarm);
//        saveAlarmList();
//        return alarm;
//    }
//
//
//    private void saveAlarmList() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(mAlarmList);
//        editor.putString("alarm list", json);
//    }
//
//    private void loadAlarmList() {
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("alarm list", null);
//        Type type = new TypeToken<ArrayList<AlarmObject>>() {}.getType();
//        mAlarmList = gson.fromJson(json, type);
//        if (mAlarmList == null) {
//            mAlarmList = new ArrayList<>();
//        }
//    }
//
//    public void removeAlarm(int position) {
//        mAlarmList.remove(position);
//    }
//
//
//    public ArrayList<AlarmObject> getAlarmList() {
//        return mAlarmList;
//    }
//
//    public ArrayList<AlarmCardView> getAlarmViewList() {
//        ArrayList<AlarmCardView> list = new ArrayList<>();
//        for (AlarmObject alarm: mAlarmList)
//            list.add(new AlarmCardView(alarm));
//        return list;
//    }
}
