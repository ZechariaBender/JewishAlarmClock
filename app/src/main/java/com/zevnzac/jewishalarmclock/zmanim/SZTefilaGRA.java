package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class SZTefilaGRA extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 6;
    }

    @Override
    public String toString() {
        return "Sof Zman Tfila GRA";
    }

    @Override
    public String toShortString() {
        return "S\"Z Tfila";
    }

    @Override
    public String toLongString() {
        return "S\"Z Tfila (Latest Shacharis) GRA";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getSofZmanTfilaGRA();
    }
}
