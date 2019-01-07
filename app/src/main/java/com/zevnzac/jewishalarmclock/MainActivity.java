package com.zevnzac.jewishalarmclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AlarmCardView> mAlarmViewlist;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton addButton;
    private AlarmList list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        list = AlarmList.get();
        if (list.getList() == null)
            loadAlarmList();
        addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAlarm();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
        saveAlarmList();
        buildRecyclerView();
    }

    private void loadAlarmList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("alarm list", null);
        Type type = new TypeToken<ArrayList<AlarmObject>>() {}.getType();
        ArrayList<String> stringList = gson.fromJson(json, type);
        if (stringList == null) {
            stringList = new ArrayList<>();
        }
        ArrayList<AlarmObject> alarmList = new ArrayList<>();
        for (String string : stringList)
            alarmList.add(new AlarmObject(string));
        list.setList(alarmList);
    }

    private void saveAlarmList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list.getStringList());
        editor.putString("alarm list", json);
    }

    private void updateAlarmViewList() {
        mAlarmViewlist = getAlarmViewList();
        mAdapter = new AlarmCardViewAdapter(mAlarmViewlist);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<AlarmCardView> getAlarmViewList() {
        ArrayList<AlarmCardView> viewlist = new ArrayList<>();
        for (AlarmObject alarm: list.getList())
            viewlist.add(new AlarmCardView(alarm));
        return viewlist;
    }

    public void removeItem(int position) {
        mAlarmViewlist.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.alarmRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        updateAlarmViewList();
        mRecyclerView.setLayoutManager(mlayoutManager);
    }

    private void openAddAlarm(){
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }
}
