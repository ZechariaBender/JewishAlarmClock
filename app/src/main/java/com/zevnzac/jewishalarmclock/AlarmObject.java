package com.zevnzac.jewishalarmclock;

import java.util.List;

public class AlarmObject {
    private List<Integer> days;
    private String notificationType;
    private int mHour, mMinute;


    public AlarmObject() {}

    public AlarmObject(int hour, int minute) {

        mHour = hour;
        mMinute = minute;
    }

    public AlarmObject(String string) {
        String[] values = string.split(":");
        mHour = Integer.parseInt(values[0]);
        mMinute = Integer.parseInt(values[1]);
    }

    public String toString() {
        String string = Integer.toString(mHour).concat(":").concat(Integer.toString(mMinute));
        return string;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setHour(int hour) {
        this.mHour = hour;
    }

    public void setMinute(int minute) {
        this.mMinute = minute;
    }

    public int getHour() { return mHour; }

    public int getMinute() { return mMinute; }


    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public enum NotificationTypes {
        RINGER("ringer"), VIBRATE("vibrate"), SILENT("silent");

        private  String code;

        private NotificationTypes (String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }

    }
}