package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class Chatzos extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 7;
    }

    @Override
    public String toString() {
        return "Chatzos HaYom";
    }

    @Override
    public String toShortString() {
        return "Chatzos";
    }

    @Override
    public String toLongString() {
        return "Chatzos HaYom (midday)";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getChatzos();
    }
}
