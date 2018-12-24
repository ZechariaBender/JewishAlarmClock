package com.zevnzac.jewishalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<AlarmCardView> mAlarmViewlist;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private FloatingActionButton addButton;

    private AlarmFactory alarmFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmFactory = AlarmFactory.getFactory();
        buildRecyclerView();
        addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAlarm();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateAlarmViewList();
    }

    public void updateAlarmViewList() {
        mAlarmViewlist = alarmFactory.getAlarmViewList();
        mAdapter = new AlarmAdapter(mAlarmViewlist);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void insertItem() {
        alarmFactory.insertAlarm(7,30);
        updateAlarmViewList();
    }

    public void removeItem(int position) {
        mAlarmViewlist.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.alarmRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        updateAlarmViewList();
        mRecyclerView.setLayoutManager(mlayoutManager);
    }

    public void openAddAlarm(){
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }
}
