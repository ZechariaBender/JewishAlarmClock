package com.zevnzac.jewishalarmclock;

//import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmCardViewAdapter extends RecyclerView.Adapter<AlarmCardViewAdapter.AlarmViewHolder> {

    private ArrayList<AlarmCardView> mAlarmList;

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTimeview, mAMPMView;

        public AlarmViewHolder(/*@NonNull*/ View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.alarmIconView);
            mTimeview = itemView.findViewById(R.id.alarmTimeView);
            mAMPMView = itemView.findViewById(R.id.AMPMView);
        }
    }

    public AlarmCardViewAdapter(ArrayList<AlarmCardView> alarmList) {
        mAlarmList = alarmList;
    }

//    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(/*@NonNull*/ ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_card_view, viewGroup, false);
        AlarmViewHolder avl = new AlarmViewHolder(v);
        return avl;
    }

    @Override
    public void onBindViewHolder(/*@NonNull*/ AlarmViewHolder alarmViewHolder, int i) {
        AlarmCardView currentAlarm = mAlarmList.get(i);
        alarmViewHolder.mImageView.setImageResource(currentAlarm.getImageResource());
        alarmViewHolder.mTimeview.setText(currentAlarm.getTime());
        if (AlarmCardView.is24HourDisplay)
            alarmViewHolder.mAMPMView.setText("");
        else
            alarmViewHolder.mAMPMView.setText(currentAlarm.getAMPM());
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }
}
