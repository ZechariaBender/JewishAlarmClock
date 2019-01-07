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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    public AlarmsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlarmsFragment.
     */
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart(){
        super.onStart();
//        saveAlarmList();
        addButton = getView().findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAlarm();
            }
        });
        buildRecyclerView();
    }

//    private void loadAlarmList() {
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("alarm list", null);
//        Type type = new TypeToken<ArrayList<AlarmObject>>() {}.getType();
//        ArrayList<String> stringList = gson.fromJson(json, type);
//        if (stringList == null) {
//            stringList = new ArrayList<>();
//        }
//        ArrayList<AlarmObject> alarmList = new ArrayList<>();
//        for (String string : stringList)
//            alarmList.add(new AlarmObject(string));
//        list.setList(alarmList);
//    }
//
//    private void saveAlarmList() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(list.getStringList());
//        editor.putString("alarm list", json);
//    }

    private void updateAlarmViewList() {
        mAlarmViewlist = getAlarmViewList();
        mAdapter = new AlarmCardViewAdapter(mAlarmViewlist);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private ArrayList<AlarmCardView> getAlarmViewList() {
        ArrayList<AlarmCardView> viewlist = new ArrayList<>();
        for (AlarmObject alarm: AlarmList.get().getList())
            viewlist.add(new AlarmCardView(alarm));
        return viewlist;
    }

    public void removeItem(int position) {
        mAlarmViewlist.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.alarmRecyclerView);
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
