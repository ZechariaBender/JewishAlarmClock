package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class Netz extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 2;
    }

    @Override
    public String toString() {
        return "Hanetz HaChama";
    }

    @Override
    public String toShortString() {
        return "Netz";
    }

    @Override
    public String toLongString() {
        return "Hanetz HaChama (sunrise)";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getSunrise();
    }
}
