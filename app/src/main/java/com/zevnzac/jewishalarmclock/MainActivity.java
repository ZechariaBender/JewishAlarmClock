package com.zevnzac.jewishalarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;
    private FloatingActionButton addButton;
    private AlarmBuilder alarmBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmBuilder = new AlarmBuilder();
        alarmBuilder.createExampleList();
        buildRecyclerView();

        addButton = findViewById(R.id.floatingActionButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });

    }

    public void insertItem(){
        AlarmBuilder.insertItem();
        TextView tv = findViewById(R.id.alarmCount);
        tv.setText(Integer.toString(AlarmBuilder.getAlarmList().size()));
    }

    public void openAddAlarm(){
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }


    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.alarmRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(alarmBuilder.getAdapter());

    }

}
