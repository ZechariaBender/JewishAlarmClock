package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class MinchaKetana extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 9;
    }

    @Override
    public String toString() {
        return "Mincha Ketana";
    }

    @Override
    public String toShortString() {
        return "Mincha Ketana";
    }

    @Override
    public String toLongString() {
        return toString();
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getMinchaKetana();
    }
}
