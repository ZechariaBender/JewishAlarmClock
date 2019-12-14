package com.zevnzac.jewishalarmclock.zmanim;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Alos extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 1;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alos HaShachar";
    }

    @Override
    public String toShortString() {
        return "Alos";
    }

    @Override
    public String toLongString() {
        return "Alos HaShachar (dawn)";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getAlosHashachar();
    }
}
