package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class Plag extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 10;
    }

    @Override
    public String toString() {
        return "Plag HaMincha";
    }

    @Override
    public String toShortString() {
        return "Plag";
    }

    @Override
    public String toLongString() {
        return "Plag HaMincha";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getPlagHamincha();
    }
}
