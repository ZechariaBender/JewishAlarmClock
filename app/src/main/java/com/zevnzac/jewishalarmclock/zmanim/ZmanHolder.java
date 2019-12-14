package com.zevnzac.jewishalarmclock.zmanim;

import android.location.Location;
import android.support.annotation.NonNull;

import net.sourceforge.zmanim.util.GeoLocation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public abstract class ZmanHolder implements Serializable {
    protected SerializableZmanimCalendar zmanimCalendar;
    protected Calendar calendar;

    public ZmanHolder() {
        zmanimCalendar = new SerializableZmanimCalendar();
        calendar = Calendar.getInstance();
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        this.zmanimCalendar.setCalendar(calendar);
    }

    public void setLocation(Location l) {
        zmanimCalendar = new SerializableZmanimCalendar(new GeoLocation("", l.getLatitude(), l.getLongitude(), 0, TimeZone.getDefault()));
        zmanimCalendar.setCalendar(calendar);
    }

    public int getCode() {
        return 0;
    }

    @NonNull
    @Override
    public abstract String toString();

    public abstract String toShortString();

    public abstract String toLongString();

    public abstract Date getTime();

    protected class SerializableZmanimCalendar
            extends net.sourceforge.zmanim.ZmanimCalendar
            implements Serializable {
        private SerializableZmanimCalendar() {
            super();
        }
        private SerializableZmanimCalendar(net.sourceforge.zmanim.util.GeoLocation location) {
            super(location);
        }
    }
}
