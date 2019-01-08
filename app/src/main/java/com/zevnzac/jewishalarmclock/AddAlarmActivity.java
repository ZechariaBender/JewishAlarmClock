package com.zevnzac.jewishalarmclock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class AddAlarmActivity extends AppCompatActivity {

    Intent goBack;
    Button cancelButton;
    Button addAlarmButton;
    TimePicker timePicker;
    AlarmObject alarmObject;

    SimpleExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_alarm);
        goBack = new Intent(this, MainTabsActivity.class);
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBack);
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
        timePicker.setIs24HourView(AlarmCardView.is24HourDisplay);
        timePicker.setHour(6);
        timePicker.setMinute(0);
    }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void addAlarm() {
            alarmObject.setHour(timePicker.getHour());
            alarmObject.setMinute(timePicker.getMinute());
            AlarmList.get().insertAlarm(alarmObject);
            startActivity(goBack);
        }

        public void showRingerTypeOptions(View v) {
            final CharSequence[] items = {" Ring "," Vibrate "," Silent "};

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Notification Type");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch(item)
                    {
                        case 0:
                            // Your code when first option seletced
                            alarmObject.setNotificationType(AlarmObject.NotificationTypes.RINGER.getCode());
                            break;
                        case 1:
                            alarmObject.setNotificationType(AlarmObject.NotificationTypes.VIBRATE.getCode());

                            break;
                        case 2:
                            alarmObject.setNotificationType(AlarmObject.NotificationTypes.SILENT.getCode());
                            break;
                    }
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        public void showDaysPicker(View v) {
            final CharSequence[] items={"Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            final boolean[] itemsChecked = new boolean[items.length];

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Pick a day");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<Integer> selectedDays = new ArrayList<Integer>();
                    for(int i=0; i<items.length; i++) {
                        if(itemsChecked[i]) {
                            selectedDays.add(1+i);
                            itemsChecked[i] = false;
                        }
                    }
                    alarmObject.setDays(selectedDays);
                }
            });
            builder.setMultiChoiceItems(items, new boolean[]{false, false, false, false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    itemsChecked[which] = isChecked;
                }
            });
            builder.show();
        }


        /*
         * Preparing the list data
         */
        private void prepareListData() {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("A");
            listDataHeader.add("B");
            listDataHeader.add("C");

            // Adding child data
            List<String> A = new ArrayList<String>();
            A.add("a");

            List<String> B = new ArrayList<String>();
            B.add("b");

            List<String> C = new ArrayList<String>();
            C.add("c");

            listDataChild.put(listDataHeader.get(0), A); // Header, Child data
            listDataChild.put(listDataHeader.get(1), B);
            listDataChild.put(listDataHeader.get(2), C);
        }
    }

