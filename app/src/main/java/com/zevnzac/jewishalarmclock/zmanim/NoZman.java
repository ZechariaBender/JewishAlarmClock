package com.zevnzac.jewishalarmclock.zmanim;

import java.io.Serializable;
import java.util.Date;

public class NoZman extends ZmanHolder implements Serializable {
    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toShortString() {
        return toString();
    }

    @Override
    public String toLongString() {
        return toString();
    }

    @Override
    public Date getTime() {
        return null;
    }
}
