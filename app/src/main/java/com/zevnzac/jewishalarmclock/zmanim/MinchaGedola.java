package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class MinchaGedola extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 8;
    }

    @Override
    public String toString() {
        return "Mincha Gedola";
    }

    @Override
    public String toShortString() {
        return "Mincha Gedola";
    }

    @Override
    public String toLongString() {
        return "Mincha Gedola (Earliest Mincha)";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getMinchaGedola();
    }
}
