package com.zevnzac.jewishalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAlarmActivity extends AppCompatActivity {

    Button addAlarmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });
    }

    public void addAlarm() {
        EditText et = (EditText)findViewById(R.id.editTime);
        String time = et.getText().toString();
        if (time.isEmpty())
            time = "12:00";
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        AlarmFactory.getFactory().insertAlarm(hour,minute);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
