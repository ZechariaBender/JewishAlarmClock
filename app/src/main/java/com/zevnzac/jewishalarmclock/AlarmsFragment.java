package com.zevnzac.jewishalarmclock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AlarmsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<AlarmCardView> mAlarmViewlist;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private FloatingActionButton addButton;
    private AlarmList list;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

    private void updateAlarmViewList() {
        mAlarmViewlist = getAlarmViewList();
        mAdapter = new AlarmCardViewAdapter(mAlarmViewlist, this.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<AlarmCardView> getAlarmViewList() {
        list = AlarmList.get();
        ArrayList<AlarmCardView> viewlist = new ArrayList<>();
        for (AlarmObject alarm: list.getList()){
            viewlist.add(new AlarmCardView(alarm));
        }
        return viewlist;
    }

    private void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.alarmRecyclerView);
        if (MainTabsActivity.nightMode)
            mRecyclerView.setBackgroundColor(getResources().getColor(R.color.background_dark));
        mRecyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(this.getContext());
        updateAlarmViewList();
        mRecyclerView.setLayoutManager(mlayoutManager);
    }

    private void openAddAlarm(){
        Intent intent = new Intent(this.getContext(), AddAlarmActivity.class);
        startActivity(intent);
    }
}
