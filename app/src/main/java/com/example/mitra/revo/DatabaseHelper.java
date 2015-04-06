package com.example.mitra.revo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Glb_Tb_Messages = "Glb_Tb_Messages";
    public static final String COLUMN_ID = "_id";
    public static final String xTitle = "xTitle";
    public static final String xDateFrom = "xDateFrom";
    public static final String xDateTo = "xDateTo";
    public static final String xType = "xType";
    public static final String xTimeFrom = "xTimeFrom";
    public static final String xTimeTo = "xTimeTo";
    public static final String xRepeatType = "xRepeatType"; // 0:norepeat, 1:everyday, 2:everyweek, 3:everyyeare
    public static final String xNotificationIntervals = "xNotificationIntervals"; //separated by commas
    public static final String xIsImportant = "xIsImportant"; //separated by commas

    private static final String DATABASE_NAME = "Messages.db";
    private static final int DATABASE_VERSION = 14;

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
            + xType + " integer null, "
            + xRepeatType + " text null, "
            + xNotificationIntervals + " text null, "
            + xIsImportant + " integer null "
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('1','Old messages are Shown here'" +
                ",'20150405','20150405','10:05','11:05','0','0','1','1')");
        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('2','We filter them based on their date!'" +
                ",'20150405','20150405','12:05','13:05','0','0','1','0')");
        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('3','You can see history of your messages!'" +
                ",'20150405','20150405','14:05','15:05','0','0','1','1')");
        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('4','All old messages are here'" +
                ",'20150405','20150405','16:05','17:05','0','0','1','0')");

        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('5','You have ACP metting today!'" +
                ",'20150420','20160406','10:05','11:05','0','0','1','1')");
        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('6','You have to meet Mary!'" +
                ",'20150420','20160406','12:05','13:05','0','0','1','0')");
        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('7','You have a to visit the doctor today!'" +
                ",'20150420','20160406','14:05','15:05','0','0','1','1')");
        database.execSQL("INSERT INTO  Glb_Tb_Messages VALUES('8','You have to buy milk!'" +
                ",'20150420','20160406','16:05','17:05','0','0','1','0')");
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
