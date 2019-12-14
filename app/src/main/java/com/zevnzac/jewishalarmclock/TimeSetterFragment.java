package com.zevnzac.jewishalarmclock;

import android.support.v4.app.Fragment;

import com.zevnzac.jewishalarmclock.zmanim.ZmanHolder;

public abstract class TimeSetterFragment extends Fragment {
    public abstract int getHour();
    public abstract int getMinute();
    public abstract ZmanHolder getZman();
    public abstract void setHour(int hour);
    public abstract void setMinute(int minute);
    public abstract void setZman(ZmanHolder zman);
}
