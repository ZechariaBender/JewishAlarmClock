package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class SZTefilaMGA extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 5;
    }

    @Override
    public String toString() {
        return "Sof Zman Tfila MGA";
    }

    @Override
    public String toShortString() {
        return "S\"Z Tfila";
    }

    @Override
    public String toLongString() {
        return "S\"Z Tfila (Latest Shacharis) MGA";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getSofZmanTfilaMGA();
    }
}
