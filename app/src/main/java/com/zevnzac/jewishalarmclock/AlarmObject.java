package com.zevnzac.jewishalarmclock;

import android.content.Context;

import com.zevnzac.jewishalarmclock.zmanim.ZmanHolder;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class AlarmObject implements Serializable {

    private String label, ringtone;
    private int enabled;
    private int id, hour, minute;
    private DaysOfWeek daysOfWeek;
    private ZmanHolder zman;

    public AlarmObject(String label, int id, int hour, int minute, int enabled, ZmanHolder zman, String ringtone, DaysOfWeek daysOfWeek) {
        this.label = label;
        this.enabled = enabled;
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.zman = zman;
        this.ringtone = ringtone;
        this.daysOfWeek = daysOfWeek;
    }

    public AlarmObject(String label, int hour, int minute, int enabled, ZmanHolder zman, String ringtone, DaysOfWeek daysOfWeek) {
        this(label,(int)(System.currentTimeMillis()/1000),hour,minute,enabled, zman,ringtone,daysOfWeek);
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public ZmanHolder getZman() {
        return zman;
    }

    public void setZman(ZmanHolder zman) {
        this.zman = zman;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(DaysOfWeek daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    static final class DaysOfWeek implements Serializable {

        private static int[] DAY_MAP = new int[]{
                Calendar.SUNDAY,
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY,
        };

        // Bitmask of all repeating days
        private int mDays;

        DaysOfWeek(int days) {
            mDays = days;
        }

        public String toString(Context context, boolean showOnce) {
            StringBuilder ret = new StringBuilder();

            // no days
            if (mDays == 0) {
                return showOnce ?
                        context.getText(R.string.once).toString() : "";
            }

            // every weekday
            if (mDays == 0x3f) {
                return context.getText(R.string.weekdays).toString();
            }

            // every day
            if (mDays == 0x7f) {
                return context.getText(R.string.every_day).toString();
            }

            // count selected days
            int dayCount = 0, days = mDays;
            while (days > 0) {
                if ((days & 1) == 1) dayCount++;
                days >>= 1;
            }

            // short or long form?
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] dayList = (dayCount > 1) ?
                    dfs.getShortWeekdays() :
                    dfs.getWeekdays();

            // selected days
            for (int i = 0; i < 7; i++) {
                if ((mDays & (1 << i)) != 0) {
                    ret.append(dayList[DAY_MAP[i]]);
                    dayCount -= 1;
                    if (dayCount > 0) ret.append(
                            context.getText(R.string.day_concat));
                }
            }
            return ret.toString();
        }


        private boolean isSet(int day) {
            return ((mDays & (1 << day)) > 0);
        }

        public void set(int day, boolean set) {
            if (set) {
                mDays |= (1 << day);
            } else {
                mDays &= ~(1 << day);
            }
        }

        public void set(DaysOfWeek dow) {
            mDays = dow.mDays;
        }

        public int getCoded() {
            return mDays;
        }

        // Returns days of week encoded in an array of booleans.
        public boolean[] getBooleanArray() {
            boolean[] ret = new boolean[7];
            for (int i = 0; i < 7; i++) {
                ret[i] = isSet(i);
            }
            return ret;
        }

        public boolean isRepeatSet() {
            return mDays != 0;
        }

        /**
         * returns number of days from today until next alarm
         * @param c must be set to today
         */
        public int getDaysToSkip(Calendar c) {
            if (mDays == 0) {
                return -1;
            }

//            int today = (c.get(Calendar.DAY_OF_WEEK) + 6) % 7;
            int today = c.get(Calendar.DAY_OF_WEEK) - 1;

            int DaysToSkip = 0;
            for (; DaysToSkip < 7; DaysToSkip++) {
                if (isSet((today + DaysToSkip) % 7)) {
                    break;
                }
            }
            return DaysToSkip;
        }
    }
}