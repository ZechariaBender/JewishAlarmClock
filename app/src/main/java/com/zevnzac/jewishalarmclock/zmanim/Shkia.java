package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class Shkia extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 11;
    }

    @Override
    public String toString() {
        return "Shkias HaChama";
    }

    @Override
    public String toShortString() {
        return "Shkia";
    }

    @Override
    public String toLongString() {
        return "Shkias HaChama (sunset)";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getSunset();
    }
}
