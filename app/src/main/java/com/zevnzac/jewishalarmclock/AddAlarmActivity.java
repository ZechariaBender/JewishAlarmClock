package com.zevnzac.jewishalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class AddAlarmActivity extends AppCompatActivity implements View.OnClickListener, MenuItem.OnMenuItemClickListener {


    //    private ListView listViewProperties;
//    private String[] properties = {"Repeat", "Alarm name", "Alarm sound", "Vibration"};
//
//
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
//
//        listViewProperties = (ListView)findViewById(R.id.listProperties);
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
//                (this, android.R.layout.simple_list_item_1, properties);
//
//        listViewProperties.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.menu_choice, menu);
        MenuItem save = menu.findItem(R.id.menuSave);
        save.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    public void repeat_onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_settings);
        popupMenu.show();

    }

    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.menuSave:
                AlarmBuilder.insertItem();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}