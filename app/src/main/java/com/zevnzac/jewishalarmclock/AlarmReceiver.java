package com.zevnzac.jewishalarmclock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static int pendingId;

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                DatabaseHelper databasehelper = DatabaseHelper.getInstance(context);
                int alarmId = intent.getExtras().getInt("AlarmId");
                pendingId = intent.getExtras().getInt("PendingId");
                mNotificationManager = (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
                AlarmObject alarm = databasehelper.findAlarmById(pendingId);
                deliverNotification(context, alarm);
                Bundle bundle = new Bundle();
                bundle.putString("ON_OFF", "addAlarm");
                if (alarm.getDaysOfWeek().isRepeatSet()) {
                    AlarmCreator ac = new AlarmCreator(context);
                    ac.setNextAlarm(alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                } else {
                    alarm.setEnabled(0);
                    databasehelper.update(alarm);
                    MainTabsActivity.refresh = true;
                    if (MainTabsActivity.isrunning)
                        context.startActivity(new Intent(context, MainTabsActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deliverNotification(Context context, AlarmObject alarm) {

        String contentText;
        if (alarm.getZman().getCode() == 0)
            contentText = "It is " + (alarm.getHour() < 10 ? "0" : "")
                    + alarm.getHour()
                    + ":"
                    + (alarm.getMinute() < 10 ? "0" : "")
                    + alarm.getMinute();
        else contentText = "It is " + AlarmCardViewAdapter.formatOffsetText(alarm);
        Intent contentIntent = new Intent(context, MainTabsActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.jac_logo_transparent_grayscale)
                .setContentTitle(alarm.getLabel())
                .setContentText(contentText)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}