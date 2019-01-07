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
            listDataHeader.add("Top 250");
            listDataHeader.add("Now Showing");
            listDataHeader.add("Coming Soon..");

            // Adding child data
            List<String> top250 = new ArrayList<String>();
            top250.add("The Shawshank Redemption");
            top250.add("The Godfather");
            top250.add("The Godfather: Part II");
            top250.add("Pulp Fiction");
            top250.add("The Good, the Bad and the Ugly");
            top250.add("The Dark Knight");
            top250.add("12 Angry Men");

            List<String> nowShowing = new ArrayList<String>();
            nowShowing.add("The Conjuring");
            nowShowing.add("Despicable Me 2");
            nowShowing.add("Turbo");
            nowShowing.add("Grown Ups 2");
            nowShowing.add("Red 2");
            nowShowing.add("The Wolverine");

            List<String> comingSoon = new ArrayList<String>();
            comingSoon.add("2 Guns");
            comingSoon.add("The Smurfs 2");
            comingSoon.add("The Spectacular Now");
            comingSoon.add("The Canyons");
            comingSoon.add("Europa Report");

            listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
            listDataChild.put(listDataHeader.get(1), nowShowing);
            listDataChild.put(listDataHeader.get(2), comingSoon);
        }
    }

