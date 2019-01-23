package com.zevnzac.jewishalarmclock;

//import android.support.annotation.NonNull;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmCardViewAdapter extends RecyclerView.Adapter<AlarmCardViewAdapter.AlarmViewHolder> {

    private ArrayList<AlarmCardView> alarmList;
    public Context context;

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView timeView, AMPMView;
        public RelativeLayout layout;

        public AlarmViewHolder(/*@NonNull*/ View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.cardViewLayout);
            imageView = itemView.findViewById(R.id.alarmIconView);
            timeView = itemView.findViewById(R.id.alarmTimeView);
            AMPMView = itemView.findViewById(R.id.AMPMView);
        }
    }

    public AlarmCardViewAdapter(ArrayList<AlarmCardView> alarmList, Context context) {
        this.alarmList = alarmList;
        this.context = context;
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

        AlarmCardView currentAlarm = alarmList.get(i);
        alarmViewHolder.timeView.setText(currentAlarm.getTime());

        if (MainTabsActivity.nightMode) {
            alarmViewHolder.imageView.setImageResource(R.drawable.ic_alarm_70);
            alarmViewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.card_view_background_dark));
        }
        else {
            alarmViewHolder.imageView.setImageResource(R.drawable.ic_alarm);
            alarmViewHolder.timeView.setTextColor(context.getResources().getColor(R.color.dark_gray_text_color));
        }
        if (MainTabsActivity.hourView)
            alarmViewHolder.AMPMView.setText("");
        else
            alarmViewHolder.AMPMView.setText(currentAlarm.getAMPM());
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }
}
