package com.zevnzac.jewishalarmclock;

public class AlarmCardView {
    private int mImageResource;
    private String mTime;
    private String AMPM;
    static boolean is24HourDisplay = true;

    public AlarmCardView(AlarmObject alarmObject) {
        mImageResource = R.drawable.ic_alarm;
        if (is24HourDisplay) {
            mTime = Integer.toString(alarmObject.getHour()) + ":"
                    + (alarmObject.getMinute() < 10 ? "0" : "")
                    + Integer.toString(alarmObject.getMinute());
            AMPM = "";
        }
        else {
            int hour = alarmObject.getHour();
            switch (hour){
                case 0:
                    hour = 12;
                    AMPM = "AM";
                    break;
                case 12:
                    hour = 12;
                    AMPM = "PM";
                    break;
                default:
                    if (hour > 12) {
                        hour -= 12;
                        AMPM = "PM";
                    } else
                        AMPM = "AM";
            }
            mTime = Integer.toString(hour) + ":"
                    + (alarmObject.getMinute() < 10 ? "0" : "")
                    + Integer.toString(alarmObject.getMinute());
        }
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getTime() {
        return mTime;
    }

    public String getAMPM() {
        return AMPM;
    }
}
