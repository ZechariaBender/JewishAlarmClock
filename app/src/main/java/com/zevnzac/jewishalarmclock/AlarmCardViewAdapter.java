package com.zevnzac.jewishalarmclock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


class AlarmCardView {
    private AlarmObject alarmObject;
    private final String AM = "AM";
    private final String PM = "PM";
    private String mTime;
    private String AMPM;

    public AlarmCardView(AlarmObject alarmObject) {
        this.alarmObject = alarmObject;
        if (MainTabsActivity._24hourView) {
            mTime = (alarmObject.getHour() < 10 ? "0" : "")
                    + alarmObject.getHour()
                    + ":"
                    + (alarmObject.getMinute() < 10 ? "0" : "")
                    + alarmObject.getMinute();
            AMPM = "";
        }
        else {
            int hour = alarmObject.getHour();
            switch (hour){
                case 0:
                    hour = 12;
                    AMPM = AM;
                    break;
                case 12:
                    hour = 12;
                    AMPM = PM;
                    break;
                default:
                    if (hour > 12) {
                        hour -= 12;
                        AMPM = PM;
                    } else
                        AMPM = AM;
            }
            mTime = hour
                    + ":"
                    + (alarmObject.getMinute() < 10 ? "0" : "")
                    + alarmObject.getMinute();
        }
    }

    public String getTime() {
        return mTime;
    }

    public String getAMPM() {
        return AMPM;
    }

    public AlarmObject getAlarmObject() {
        return alarmObject;
    }

    public void setAlarmObject(AlarmObject alarmObject) {
        this.alarmObject = alarmObject;
    }

}

public class AlarmCardViewAdapter extends RecyclerView.Adapter<AlarmCardViewAdapter.AlarmViewHolder> {

    private final CallBack mCallBack;
    public static ArrayList<AlarmCardView> alarmList;


    AlarmCardViewAdapter(ArrayList<AlarmObject> alarmObjectList, CallBack mCallBack) {
        alarmList = new ArrayList<>();
        for (AlarmObject alarm: alarmObjectList)
            alarmList.add(new AlarmCardView(alarm));
        this.mCallBack = mCallBack;
    }

    public interface CallBack {
        void onAlarmClicked(AlarmObject object, int position);
        void enableAlarm(AlarmObject timeItem);
        void disableAlarm(AlarmObject timeItem);
        Context getContext();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView imageView;
        private TextView labelTextView, timeTextView, AMPMTextView, zmanTextView, repeatTextView;
        private LinearLayout regularView;
        private RelativeLayout layout;
        private Switch aSwitch;
        private boolean switchClicked;

        AlarmViewHolder(final View itemView) {

            super(itemView);
            switchClicked = false;
            itemView.setOnClickListener(this);
            layout = itemView.findViewById(R.id.cardViewLayout);
            imageView = itemView.findViewById(R.id.alarmIconView);
            labelTextView = itemView.findViewById(R.id.label_textview);
            regularView = itemView.findViewById(R.id.regular_textview);
            timeTextView = itemView.findViewById(R.id.time_textview);
            AMPMTextView = itemView.findViewById(R.id.AMPM_textview);
            zmanTextView = itemView.findViewById(R.id.zman_textview);
            repeatTextView = itemView.findViewById(R.id.repeat_textview);
            aSwitch = itemView.findViewById(R.id.enable_switch);
            final AlarmViewHolder holder = this;
            aSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setColor(holder, isChecked);
                    int position = getLayoutPosition();
                    if (switchClicked) {
                        if (isChecked)
                            mCallBack.enableAlarm(alarmList.get(position).getAlarmObject());
                        else mCallBack.disableAlarm(alarmList.get(position).getAlarmObject());
                    }
                }
            });
            switchClicked = true;
        }

        @Override
        public void onClick(View v) {

            int position = getLayoutPosition();
            mCallBack.onAlarmClicked(alarmList.get(position).getAlarmObject(), position);
        }
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alarm_card_view, viewGroup, false);
        return new AlarmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder avh, int i) {

        AlarmCardView currentAlarm = alarmList.get(i);
        AlarmObject alarm = currentAlarm.getAlarmObject();
        avh.labelTextView.setText(alarm.getLabel());
        avh.labelTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        if (avh.labelTextView.length() == 0)
            avh.labelTextView.setVisibility(View.GONE);
        if (alarm.getZman().getCode() == 0) {
            avh.zmanTextView.setVisibility(View.GONE);
            avh.timeTextView.setText(currentAlarm.getTime());
            if (MainTabsActivity._24hourView)
                avh.AMPMTextView.setText("");
            else
                avh.AMPMTextView.setText(currentAlarm.getAMPM());
        } else {
            avh.regularView.setVisibility(View.GONE);
            avh.zmanTextView.setText(formatOffsetText(alarm));
        }
        avh.repeatTextView.setText(alarm.getDaysOfWeek()
                .toString(mCallBack.getContext(),true));
        avh.repeatTextView.setTypeface(null, Typeface.ITALIC);
        avh.aSwitch.setChecked(alarm.getEnabled() > 0);
        setColor(avh, avh.aSwitch.isChecked());
    }

    static String formatOffsetText(AlarmObject alarm) {

        String hourText, minuteText, commaText, beforeAfterText, zmanText;
        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        boolean after = false;

        if (hour + minute < 0) {
            hour = -hour;
            minute = -minute;
            after = true;
        }

        hourText = (hour == 0) ? "" : (hour == 1) ? hour + " hour" : hour + " hours";
        minuteText = (minute == 0) ? "": (minute == 1) ? minute + " minute" : minute + " minutes";
        commaText = (hour == 0 || minute == 0) ? "" : ", ";

        if (hour == 0 && minute == 0) {
            zmanText = alarm.getZman().toString();
            beforeAfterText = "";
        }
        else {
            if (hour == 0 || minute == 0)
                zmanText = alarm.getZman().toShortString();
            else zmanText = alarm.getZman().toString();
            if (after)
                beforeAfterText = " after ";
            else beforeAfterText = " before ";
        }
        return hourText + commaText + minuteText + beforeAfterText + zmanText;
    }

    @Override
    public int getItemCount() {

        return alarmList.size();
    }

    private void setColor(AlarmViewHolder holder, boolean ischecked) {

        Resources res = mCallBack.getContext().getResources();
        if (MainTabsActivity.nightMode) {
            if (ischecked) {
                holder.layout.setBackgroundColor(res.getColor(R.color.card_view_background_dark));
                holder.imageView.setImageResource(R.drawable.ic_alarm_light);
                holder.timeTextView.setTextColor(res.getColor(R.color.text_color_light));
                holder.AMPMTextView.setTextColor(res.getColor(R.color.text_color_light));
                holder.zmanTextView.setTextColor(res.getColor(R.color.text_color_light));
                holder.repeatTextView.setTextColor(res.getColor(R.color.text_color_light));
            } else {
                holder.layout.setBackgroundColor(res.getColor(R.color.card_view_background_dark_off));
                holder.imageView.setImageResource(R.drawable.ic_alarm_dark);
                holder.timeTextView.setTextColor(res.getColor(R.color.text_color_dark));
                holder.AMPMTextView.setTextColor(res.getColor(R.color.text_color_dark));
                holder.zmanTextView.setTextColor(res.getColor(R.color.text_color_dark));
                holder.repeatTextView.setTextColor(res.getColor(R.color.text_color_dark));
            }
        } else {
            if (ischecked) {
                holder.layout.setBackgroundColor(res.getColor(R.color.card_view_background_light));
                holder.imageView.setImageResource(R.drawable.ic_alarm_dark);
                holder.timeTextView.setTextColor(res.getColor(R.color.text_color_dark));
                holder.AMPMTextView.setTextColor(res.getColor(R.color.text_color_dark));
                holder.zmanTextView.setTextColor(res.getColor(R.color.text_color_dark));
                holder.repeatTextView.setTextColor(res.getColor(R.color.text_color_dark));
            } else {
                holder.layout.setBackgroundColor(res.getColor(R.color.card_view_background_light_off));
                holder.imageView.setImageResource(R.drawable.ic_alarm_medium);
                holder.timeTextView.setTextColor(res.getColor(R.color.text_color_medium));
                holder.AMPMTextView.setTextColor(res.getColor(R.color.text_color_medium));
                holder.zmanTextView.setTextColor(res.getColor(R.color.text_color_medium));
                holder.repeatTextView.setTextColor(res.getColor(R.color.text_color_medium));
            }
        }
    }

    void addAlarm(AlarmObject alarm) {

        alarmList.add(new AlarmCardView(alarm));
    }

    void updateAlarm(AlarmObject alarm, int position) {

        alarmList.remove(position);
        alarmList.add(position, new AlarmCardView(alarm));
    }

    void removeAlarm(int position) {

        try {
            mCallBack.disableAlarm(alarmList.get(position).getAlarmObject());
            alarmList.remove(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

