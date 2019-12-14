package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class SZKShGRA extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 4;
    }

    @Override
    public String toString() {
        return "Sof Zman K\"Sh GRA";
    }

    @Override
    public String toShortString() {
        return "S\"Z K\"Sh";
    }

    @Override
    public String toLongString() {
        return "Sof Zman K\"Sh (Latest Shema) GRA";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getSofZmanShmaGRA();
    }
}
