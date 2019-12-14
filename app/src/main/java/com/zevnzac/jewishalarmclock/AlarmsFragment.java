package com.zevnzac.jewishalarmclock;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmsFragment extends Fragment implements AlarmCardViewAdapter.CallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private AlarmCardViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;
    private ArrayList<AlarmObject> alarmList;
    private DatabaseHelper databasehelper;
    private AlarmCreator creator;
    private boolean firstRun = true;
    private SwipeController swipeController = null;

    public AlarmsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AlarmsFragment newInstance(String param1, String param2) {
        AlarmsFragment fragment = new AlarmsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        databasehelper = DatabaseHelper.getInstance(getActivity());
        creator = new AlarmCreator(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainTabsActivity.getHourView(getActivity());
        return inflater.inflate(R.layout.fragment_alarms, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAlarmClicked(AlarmObject object, int position) {
        Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
        intent.putExtra("screenType", "edit");
        intent.putExtra("AlarmEdit", object);
        intent.putExtra("position", position);
        startActivityForResult(intent, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void enableAlarm(AlarmObject alarm) {

        alarm.setEnabled(1);
        databasehelper.update(alarm);
        creator.setNextAlarm(alarm, 0);
    }

    @Override
    public void disableAlarm(AlarmObject timeItem) {

        timeItem.setEnabled(0);
        databasehelper.update(timeItem);
        creator.cancelAlarm(timeItem);
        sendIntent(timeItem, "off");

    }
    private void sendIntent(AlarmObject alarm, String intentType) {

        Intent intent1 = new Intent(getActivity(), AlarmReceiver.class);
        intent1.putExtra("intentType", intentType);
        intent1.putExtra("AlarmId", alarm.getId());
        getActivity().sendBroadcast(intent1);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
        addButton = getView().findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAlarm();
            }
        });
        if (MainTabsActivity.nightMode)
            addButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_black));
        else
            addButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white));
        buildRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstRun)
            firstRun = false;
        else
            getActivity().recreate();
    }

    private void updateAlarmViewList() {
        alarmList = databasehelper.getAlarmList();
        adapter = new AlarmCardViewAdapter(alarmList, this);
        recyclerView.setAdapter(adapter);
    }

    private void buildRecyclerView() {
        recyclerView = getView().findViewById(R.id.alarmRecyclerView);
        if (MainTabsActivity.nightMode)
            recyclerView.setBackgroundColor(getResources().getColor(R.color.background_dark));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
        swipeController = new SwipeController(new SwipeController.SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                AlarmObject alarm = AlarmCardViewAdapter.alarmList.get(position).getAlarmObject();
                adapter.removeAlarm(position);
                databasehelper.delete(alarm.getId());
                creator.cancelAlarm(alarm);
                getActivity().recreate();
                Toast.makeText(getActivity(), "Alarm deleted", Toast.LENGTH_SHORT).show();
            }
        }, getActivity());

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        updateAlarmViewList();
        recyclerView.setLayoutManager(layoutManager);

    }

    private void openAddAlarm(){
        Intent intent = new Intent(this.getContext(), AddAlarmActivity.class);
        intent.putExtra("screenType", "addAlarm");
        startActivityForResult(intent, 0);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AlarmObject alarm;
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            alarm = (AlarmObject) data.getSerializableExtra("Alarm");

            if (!alreadyExists(alarm)) {
                alarmList.add(alarm);
                adapter.addAlarm(alarm);
                enableAlarm(alarm);
                adapter.notifyDataSetChanged();
                databasehelper.insert(alarm);
                creator.setNextAlarm(alarm, 0);
            }


        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            alarm = (AlarmObject) data.getSerializableExtra("Alarm");

            if (!alreadyExists(alarm)) {
                int position = data.getExtras().getInt("position");
                adapter.updateAlarm(alarm, position);
                adapter.notifyDataSetChanged();
                databasehelper.update(alarm);
                if (alarm.getEnabled() == 1) {
                    creator.setNextAlarm(alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                }
            }

        }
    }
    private boolean alreadyExists(AlarmObject alarm) {

        boolean contain = false;
        for (AlarmObject alar : alarmList) {
            if (alar.getId() != alarm.getId() && alar.getHour() == alarm.getHour() && alar.getMinute() == alarm.getMinute() && alar.getZman().getCode() == alarm.getZman().getCode())
                contain = true;
        }
        if (contain) {
            Toast.makeText(getActivity(), "You have already have an alarm at this hour", Toast.LENGTH_SHORT).show();
        }
        return contain;
    }

}
