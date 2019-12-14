package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class SZKShMGA extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 3;
    }

    @Override
    public String toString() {
        return "Sof Zman K\"Sh MGA";
    }

    @Override
    public String toShortString() {
        return "S\"Z K\"Sh";
    }

    @Override
    public String toLongString() {
        return "Sof Zman K\"Sh (Latest Shema) MGA";
    }

    @Override
    public Date getTime() {
        return zmanimCalendar.getSofZmanShmaMGA();
    }
}
