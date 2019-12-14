package com.zevnzac.jewishalarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.zevnzac.jewishalarmclock.zmanim.Alos;
import com.zevnzac.jewishalarmclock.zmanim.ZmanHolder;
import com.zevnzac.jewishalarmclock.zmanim.ZmanimList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZmanBasedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZmanBasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZmanBasedFragment extends TimeSetterFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @SuppressLint("StaticFieldLeak")
    private static ZmanHolder zman;
    private static int hour, minute;
    private static NumberPicker hp, mp;
    private static boolean after = false;
    private static Spinner beforeAfterSpinner;

    public ZmanBasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZmanBasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZmanBasedFragment newInstance(String param1, String param2) {
        ZmanBasedFragment fragment = new ZmanBasedFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zman_based, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hp = Objects.requireNonNull(getView()).findViewById(R.id.hour_picker);
        hp.setMinValue(0);
        hp.setMaxValue(23);
        hp.setValue(hour);
        mp = getView().findViewById(R.id.minute_picker);
        mp.setMinValue(0);
        mp.setMaxValue(59);
        mp.setValue(minute);

        beforeAfterSpinner = getView().findViewById(R.id.before_after_spinner);
        List<String> list = new ArrayList<>();
        list.add("before");
        list.add("after");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        beforeAfterSpinner.setAdapter(adapter);
        if (after)
            beforeAfterSpinner.setSelection(1);
        beforeAfterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                after = beforeAfterSpinner.getSelectedItem() == "after";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        final Spinner zmanimSpinner = getView().findViewById(R.id.zmanim_spinner);
        ArrayAdapter<ZmanHolder> zmanimAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, new ZmanimList().getList());
        zmanimAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        zmanimSpinner.setAdapter(zmanimAdapter);
        if (zman == null)
            zman = new Alos();
        int position = zman.getCode() - 1;
        zmanimSpinner.setSelection(position);
        zmanimSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zman = (ZmanHolder) zmanimSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
    public int getHour() {
        int h = hp.getValue();
        if (after)
            h = -h;
        return h;
    }

    @Override
    public int getMinute() {
        int m = mp.getValue();
        if (after)
            m = -m;
        return m;
    }

    @Override
    public ZmanHolder getZman() {
        return zman;
    }


    @Override
    public void setHour(int hour) {
        if (hour < 0) {
            hour = -hour;
            after = true;
        } else after = false;
        ZmanBasedFragment.hour = hour;
    }

    @Override
    public void setMinute(int minute) {
        if (minute < 0) {
            minute = -minute;
            after = true;
        } else after = false;
        ZmanBasedFragment.minute = minute;
    }

    @Override
    public void setZman(ZmanHolder zman) {
        ZmanBasedFragment.zman = zman;
    }
}





