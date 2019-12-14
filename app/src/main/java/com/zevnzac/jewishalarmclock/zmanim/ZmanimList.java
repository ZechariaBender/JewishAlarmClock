package com.zevnzac.jewishalarmclock.zmanim;

import android.location.Location;

import com.zevnzac.jewishalarmclock.MainTabsActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class ZmanimList {
    private ArrayList<ZmanHolder> list;

    public ZmanimList() {
        this (Calendar.getInstance(), MainTabsActivity.getLocation());
    }

    public ZmanimList(Calendar c, Location l) {

        list = new ArrayList<>();
        list.add(new Alos());
        list.add(new Netz());
        list.add(new SZKShMGA());
        list.add(new SZKShGRA());
        list.add(new SZTefilaMGA());
        list.add(new SZTefilaGRA());
        list.add(new Chatzos());
        list.add(new MinchaGedola());
        list.add(new MinchaKetana());
        list.add(new Plag());
        list.add(new Shkia());
        list.add(new Tzeis());
        list.add(new TzeisRT());

        setCalendar(c);
        setLocation(l);
    }

    public ArrayList<ZmanHolder> getList() {
        return new ArrayList<>(list);
    }

    public String[] getStringList() {
        String[] stringList = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
            stringList[i] = (list.get(i).toLongString());
        return stringList;
    }

    public void setCalendar(Calendar calendar) {
        for (ZmanHolder zmanHolder : list)
            zmanHolder.setCalendar(calendar);
    }

    public void setLocation(Location location) {
        for (ZmanHolder zmanHolder : list)
            zmanHolder.setLocation(location);
    }

    public static ZmanHolder getZman(int code) {
        switch (code) {
            case 0: return new NoZman();
            case 1: return new Alos();
            case 2: return new Netz();
            case 3: return new SZKShMGA();
            case 4: return new SZKShGRA();
            case 5: return new SZTefilaMGA();
            case 6: return new SZTefilaGRA();
            case 7: return new Chatzos();
            case 8: return new MinchaGedola();
            case 9: return new MinchaKetana();
            case 10: return new Plag();
            case 11: return new Shkia();
            case 12: return new Tzeis();
            case 13: return new TzeisRT();
            default: return new NoZman();
        }
    }
    public static int getCoded(ZmanHolder zman) {
        return zman.getCode();
    }
}
