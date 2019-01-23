package com.zevnzac.jewishalarmclock;

public class AlarmCardView {
    private String mTime;
    private String AMPM;

    public AlarmCardView(AlarmObject alarmObject) {
        if (MainTabsActivity.hourView) {
            mTime = (alarmObject.getHour() < 10 ? "0" : "")
                    + Integer.toString(alarmObject.getHour())
                    + ":"
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
            mTime = Integer.toString(hour)
                    + ":"
                    + (alarmObject.getMinute() < 10 ? "0" : "")
                    + Integer.toString(alarmObject.getMinute());
        }
    }

    public String getTime() {
        return mTime;
    }

    public String getAMPM() {
        return AMPM;
    }
}
