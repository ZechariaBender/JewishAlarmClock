package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class Tzeis extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 12;
    }

    @Override
    public String toString() {
        return "Tzeis Hakochavim";
    }

    @Override
    public String toShortString() {
        return "Tzeis";
    }

    @Override
    public String toLongString() {
        return "Tzeis Hakochavim (nightfall)";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getTzais();
    }
}
