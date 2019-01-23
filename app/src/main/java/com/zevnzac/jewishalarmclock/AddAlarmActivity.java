package com.zevnzac.jewishalarmclock;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class AddAlarmActivity extends AppCompatActivity {

    Button cancelButton;
    Button addAlarmButton;
    TimePicker timePicker;
    AlarmObject alarmObject;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });
        alarmObject = new AlarmObject();
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(MainTabsActivity.hourView);
        timePicker.setHour(6);
        timePicker.setMinute(0);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new alarmPreferenceFragment()).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addAlarm() {
        alarmObject.setHour(timePicker.getHour());
        alarmObject.setMinute(timePicker.getMinute());
        AlarmList.get().insertAlarm(alarmObject);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class alarmPreferenceFragment extends PreferenceFragment {
        private SharedPreferences sharedPreferences;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_alarm);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        }
    }

//    public void showRingerTypeOptions(View v) {
//        final CharSequence[] items = {" Ring ", " Vibrate ", " Silent "};
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Notification Type");
//        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//                switch (item) {
//                    case 0:
//                        // Your code when first option seletced
//                        alarmObject.setNotificationType(AlarmObject.NotificationTypes.RINGER.getCode());
//                        break;
//                    case 1:
//                        alarmObject.setNotificationType(AlarmObject.NotificationTypes.VIBRATE.getCode());
//
//                        break;
//                    case 2:
//                        alarmObject.setNotificationType(AlarmObject.NotificationTypes.SILENT.getCode());
//                        break;
//                }
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }
//
//    public void showDaysPicker(View v) {
//        final CharSequence[] items = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//        final boolean[] itemsChecked = new boolean[items.length];
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Pick a day");
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                List<Integer> selectedDays = new ArrayList<Integer>();
//                for (int i = 0; i < items.length; i++) {
//                    if (itemsChecked[i]) {
//                        selectedDays.add(1 + i);
//                        itemsChecked[i] = false;
//                    }
//                }
//                alarmObject.setDays(selectedDays);
//            }
//        });
//        builder.setMultiChoiceItems(items, new boolean[]{false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                itemsChecked[which] = isChecked;
//            }
//        });
//        builder.show();
//    }
}