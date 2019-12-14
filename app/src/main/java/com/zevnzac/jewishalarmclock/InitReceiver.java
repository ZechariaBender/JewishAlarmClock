package com.zevnzac.jewishalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class InitReceiver extends BroadcastReceiver {

    private DatabaseHelper databasehelper;
    private AlarmCreator creator;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        databasehelper = DatabaseHelper.getInstance(context);
        creator = new AlarmCreator(context);
        for (AlarmObject alarm: databasehelper.getAlarmList())
            if (alarm.getEnabled() == 1)
                creator.setNextAlarm(alarm, 0);
    }
}
