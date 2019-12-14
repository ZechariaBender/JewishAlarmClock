package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class TzeisRT extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 13;
    }

    @Override
    public String toString() {
        return "Tzeis Hakochavim RT";
    }

    @Override
    public String toShortString() {
        return "Tzeis RT";
    }

    @Override
    public String toLongString() {
        return "Tzeis Hakochavim (nightfall) RT";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getTzais72();
    }
}
