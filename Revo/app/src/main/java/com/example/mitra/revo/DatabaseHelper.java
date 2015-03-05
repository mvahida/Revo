package com.example.mitra.revo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Glb_Tb_Messages = "Glb_Tb_Messages";
    public static final String COLUMN_ID = "_id";
    public static final String xTitle = "xTitle";
    public static final String xDateFrom = "xDateFrom";
    public static final String xDateTo = "xDateTo";
    public static final String xTimeFrom = "xTimeFrom";
    public static final String xTimeTo = "xTimeTo";
    public static final String xLocation = "xLocation";
    public static final String xRepeatType = "xRepeatType"; // 0:norepeat, 1:everyday, 2:everyweek, 3:everyyeare
    public static final String xNotificationIntervals = "xNotificationIntervals"; //separated by commas

    private static final String DATABASE_NAME = "Messages.db";
    private static final int DATABASE_VERSION = 11;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + Glb_Tb_Messages
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + xTitle + " text null, "
            + xDateFrom + " text null, "
            + xDateTo + " text null, "
            + xTimeFrom + " text null, "
            + xTimeTo + " text null, "
            + xLocation + " text null, "
            + xRepeatType + " text null, "
            + xNotificationIntervals + " text null"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Glb_Tb_Messages);
        onCreate(db);
    }

}
