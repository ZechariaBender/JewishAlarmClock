package com.zevnzac.jewishalarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.zevnzac.jewishalarmclock.zmanim.ZmanHolder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegularFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegularFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegularFragment extends TimeSetterFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @SuppressLint("StaticFieldLeak")
    private static TimePicker timePicker;
    private static ZmanHolder zman;
    private static int hour, minute;

    private OnFragmentInteractionListener mListener;

    public RegularFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegularFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegularFragment newInstance(String param1, String param2) {
        RegularFragment fragment = new RegularFragment();
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
        return inflater.inflate(R.layout.fragment_regular, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(MainTabsActivity._24hourView);
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int getHour() {
        return timePicker.getHour();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int getMinute() {
        return timePicker.getMinute();
    }

    @Override
    public ZmanHolder getZman() {
        return zman;
    }

    @Override
    public void setHour(int hour) {
        RegularFragment.hour = hour;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setMinute(int minute) {
        RegularFragment.minute = minute;
    }

    @Override
    public void setZman(ZmanHolder zman) {
        RegularFragment.zman = zman;
    }
}
