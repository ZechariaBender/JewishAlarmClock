package com.zevnzac.jewishalarmclock;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.zevnzac.jewishalarmclock.zmanim.ZmanHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmCreator {

    private Context context;
    private NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    public AlarmCreator(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setNextAlarm(AlarmObject alarm, int flags) {
        for (int i = 0; i < 10; i++) {
            Log.d("AlarmCreator", "setNextAlarm" + i);
        }
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("intentType", "addAlarm");
        intent.putExtra("PendingId", alarm.getId());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, alarm.getId(),
                intent, flags);

        long time = calculateNextAlarm(alarm, Calendar.getInstance(), Calendar.getInstance(), 10).getTimeInMillis();
        setAlarm(alarmIntent, time);
        popAlarmSetToast(context, time);
    }

    private static Calendar calculateNextAlarm(AlarmObject alarm, Calendar nowCalendar, Calendar alarmCalendar, int depth) {

        if (depth < 0)
            return alarmCalendar;
        AlarmObject.DaysOfWeek daysOfWeek = alarm.getDaysOfWeek();
        int addDays = daysOfWeek.getDaysToSkip(alarmCalendar);
        if (addDays > 0)
            alarmCalendar.add(Calendar.DAY_OF_WEEK, addDays);
        int alarmHour, alarmMinute;
        ZmanHolder zman = alarm.getZman();
        int day = alarmCalendar.get(Calendar.DAY_OF_YEAR);
        if (zman.getCode() == 0) {
            alarmHour = alarm.getHour();
            alarmMinute = alarm.getMinute();
        } else {
            zman.setCalendar(alarmCalendar);
            zman.setLocation(MainTabsActivity.getLocation());
            alarmCalendar.setTime(zman.getTime());
            alarmHour = alarmCalendar.get(Calendar.HOUR_OF_DAY) - alarm.getHour();
            alarmMinute = alarmCalendar.get(Calendar.MINUTE) - alarm.getMinute();
        }
        alarmCalendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        alarmCalendar.set(Calendar.MINUTE, alarmMinute);
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.MILLISECOND, 0);
        if (alarmCalendar.get(Calendar.DAY_OF_YEAR) < day)
            alarmCalendar.add(Calendar.DAY_OF_WEEK, 1);
        if (alarmCalendar.get(Calendar.DAY_OF_YEAR) > day)
            alarmCalendar.add(Calendar.DAY_OF_WEEK, -1);
        if (alarmCalendar.getTime().getTime() <= nowCalendar.getTime().getTime()) {
            alarmCalendar.add(Calendar.DAY_OF_WEEK, 1);
            addDays = daysOfWeek.getDaysToSkip(alarmCalendar);
            if (addDays > 0)
                alarmCalendar.add(Calendar.DAY_OF_WEEK, addDays);
            return calculateNextAlarm(alarm, nowCalendar, alarmCalendar, depth - 1);
        }
        return alarmCalendar;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(PendingIntent alarmIntent, long time) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                time, alarmIntent);
        mNotificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {

        mNotificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void cancelAlarm(AlarmObject alarm) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        int alarmId = alarm.getId();
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmIntent);
    }

    private static void popAlarmSetToast (Context context,long timeInMillis){
        String toastText = formatTimeToAlarm(context, timeInMillis);
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        toast.show();
    }

    static String formatTimeToAlarm(Context context, long timeInMillis){
        long timeToAlarm = timeInMillis - System.currentTimeMillis();
        long hours = timeToAlarm / (1000 * 60 * 60);
        long minutes = timeToAlarm / (1000 * 60) % 60;
        long days = hours / 24;
        hours = hours % 24;


        String alarmTime;
        if (MainTabsActivity._24hourView)
            alarmTime = new SimpleDateFormat("HH:mm, MMM d yyyy",
                    Locale.getDefault()).format(new Date(timeInMillis));
        else
            alarmTime = new SimpleDateFormat("H:mm a, MMM d yyyy",
                    Locale.getDefault()).format(new Date(timeInMillis));

        String dayText =
                (days == 0) ? "":
                (days == 1) ? context.getString(R.string.day) :
                        context.getString(R.string.days, Long.toString(days));

        String minText = (minutes == 0) ? "" :
                (minutes == 1) ? context.getString(R.string.minute) :
                        context.getString(R.string.minutes, Long.toString(minutes));

        String hourText = (hours == 0) ? "" :
                (hours == 1) ? context.getString(R.string.hour) :
                        context.getString(R.string.hours, Long.toString(hours));

        boolean dispDays = days > 0;
        boolean dispHour = hours > 0;
        boolean dispMinute = minutes > 0;

        int index = (dispDays ? 1 : 0) |
                (dispHour ? 2 : 0) |
                (dispMinute ? 4 : 0);

        String[] formats = context.getResources().getStringArray(R.array.alarm_set);
        return "Alarm set for " + alarmTime + " (" + String.format(formats[index], dayText, hourText, minText) + " from now).";
    }
}
