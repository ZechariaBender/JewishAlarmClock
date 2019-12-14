package com.zevnzac.jewishalarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zevnzac.jewishalarmclock.zmanim.ZmanimList;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "db_alarm";
    private static final String TABLE_NAME = "alarm";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "alarm_name";
    private static final String COL_HOUR = "hour";
    private static final String COL_MINUTE = "minute";
    private static final String COL_TOGGLE = "toggle";
    private static final String COL_ZMAN = "zman";
    private static final String COL_RINGTONE = "ringtone";
    private static final String COL_DAYS = "days";


    private Context context;
    private static DatabaseHelper instance;

    // this string defines table and data type use for onCreate method
    private String CREATE_TABLE_ALARM = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_NAME + " TEXT, "
            + COL_ID + " INTEGER, "
            + COL_HOUR + " INTEGER, "
            + COL_MINUTE + " INTEGER, "
            + COL_TOGGLE + " INTEGER, "
            + COL_ZMAN + " INTEGER, "
            + COL_RINGTONE + " TEXT, "
            + COL_DAYS + " INTEGER) ";


    public static synchronized DatabaseHelper getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(String.format(" DROP TABLE IF EXISTS %s", CREATE_TABLE_ALARM));
        onCreate(db);

    }

    public void insert(AlarmObject alarm) {

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_NAME, alarm.getLabel());
            values.put(COL_ID, alarm.getId());
            values.put(COL_HOUR, alarm.getHour());
            values.put(COL_MINUTE, alarm.getMinute());
            values.put(COL_TOGGLE, alarm.getEnabled());
            values.put(COL_ZMAN, alarm.getZman().getCode());
            values.put(COL_RINGTONE, alarm.getRingtone());
            values.put(COL_DAYS, alarm.getDaysOfWeek().getCoded());
            db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();

            }
        }


    }

    public void update(AlarmObject alarm) {

        SQLiteDatabase db = null;
        String where = COL_ID + " = " + alarm.getId();
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_NAME, alarm.getLabel());
            values.put(COL_ID, alarm.getId());
            values.put(COL_HOUR, alarm.getHour());
            values.put(COL_MINUTE, alarm.getMinute());
            values.put(COL_TOGGLE, alarm.getEnabled());
            values.put(COL_ZMAN, alarm.getZman().getCode());
            values.put(COL_RINGTONE, alarm.getRingtone());
            values.put(COL_DAYS, alarm.getDaysOfWeek().getCoded());
            db.update(TABLE_NAME, values, where, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();

            }
        }


    }

    public void delete(int alarmId) {

        SQLiteDatabase db = null;
        String where = COL_ID + " = " + alarmId;

        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_NAME, where, null);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


    }

    public AlarmObject findAlarmById(int alarmId) {

        SQLiteDatabase db = null;
        AlarmObject alarm = null;
        try {
            db = this.getReadableDatabase();
            Cursor cursor =
                    db.query(TABLE_NAME,
                            new String[] {"*"},
                            COL_ID + "=?",
                            new String[] {String.valueOf(alarmId)},
                            null, //
                            null, //
                            null, //
                            null); //

            if (cursor != null)
                cursor.moveToFirst();

            alarm = new AlarmObject(
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    ZmanimList.getZman(cursor.getInt(5)),
                    cursor.getString(6),
                    new AlarmObject.DaysOfWeek(cursor.getInt(7)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return alarm;
    }

    public ArrayList<AlarmObject> getAlarmList() {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AlarmObject> alarmArrayList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    AlarmObject alarm = new AlarmObject(
                            cursor.getString(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            ZmanimList.getZman(cursor.getInt(5)),
                            cursor.getString(6),
                            new AlarmObject.DaysOfWeek(cursor.getInt(7)));
                    alarmArrayList.add(alarm);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getAlarmList: exception cause " + e.getCause() + " message " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return alarmArrayList;
    }

}