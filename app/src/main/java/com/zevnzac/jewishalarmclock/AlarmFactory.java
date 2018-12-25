package com.zevnzac.jewishalarmclock;


import java.util.ArrayList;

public class AlarmFactory {
    private static AlarmFactory factory;

    public class AlarmObject {
        private int mHour, mMinute;
        public AlarmObject(int hour, int minute) {
            mHour = hour;
            mMinute = minute;
        }
        public int getHour() { return mHour; }

        public int getMinute() { return mMinute; }
    }

    private ArrayList<AlarmObject> mAlarmList;

    private AlarmFactory(){
        mAlarmList = new ArrayList<>();
    }
    public static synchronized AlarmFactory getFactory() {
        if(factory == null) factory = new AlarmFactory();
        return factory;
    }

    public AlarmObject insertAlarm(int hour, int minute) {
        AlarmObject alarm = new AlarmObject(hour, minute);
        mAlarmList.add(alarm);
        return alarm;
    }

    public void removeAlarm(int position) {
        mAlarmList.remove(position);
    }


    public ArrayList<AlarmObject> getAlarmList() {
        return mAlarmList;
    }
    
    public ArrayList<AlarmCardView> getAlarmViewList() {
        ArrayList<AlarmCardView> list = new ArrayList<>();
        for (AlarmObject alarm: mAlarmList)
            list.add(new AlarmCardView(
                    R.drawable.ic_alarm,
                    Integer.toString(alarm.getHour()) + ":" + (alarm.getMinute() < 10 ? "0" : "") + Integer.toString(alarm.getMinute()),
                    "AM"));
        return list;
    }
}
