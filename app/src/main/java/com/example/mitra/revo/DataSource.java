package com.example.mitra.revo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = {DatabaseHelper.COLUMN_ID,
            DatabaseHelper.xTitle, DatabaseHelper.xDateFrom, DatabaseHelper.xDateTo,
            DatabaseHelper.xTimeFrom, DatabaseHelper.xTimeTo, DatabaseHelper.xType,
            DatabaseHelper.xNotificationIntervals, DatabaseHelper.xRepeatType, DatabaseHelper.xIsImportant};

    public DataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Message createMessage(String repeatType, String notificationIntervals, String dateFrom
            , String dateTo, String timeFrom, String timeTo, int edt_type, String edt_title, int isimportant) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.xTitle, edt_title);
        values.put(DatabaseHelper.xDateFrom, dateFrom);
        values.put(DatabaseHelper.xDateTo, dateTo);
        values.put(DatabaseHelper.xTimeFrom, timeFrom);
        values.put(DatabaseHelper.xTimeTo, timeTo);
        values.put(DatabaseHelper.xType, edt_type);
        values.put(DatabaseHelper.xRepeatType, repeatType);
        values.put(DatabaseHelper.xNotificationIntervals, notificationIntervals);
        values.put(DatabaseHelper.xIsImportant, isimportant);
        long insertId = database.insert(DatabaseHelper.Glb_Tb_Messages,
                null, values);
        Cursor cursor = database.query(DatabaseHelper.Glb_Tb_Messages,
                allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        Message newMessage = cursorToMessage(cursor);
        cursor.close();
        return newMessage;
    }

    public void delete(Message message) {
        long id = message.getId();
        database.delete(DatabaseHelper.Glb_Tb_Messages,
                DatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    public void UpdateRecord(Message message, ContentValues values) {
        long id = message.getId();
        database.update(DatabaseHelper.Glb_Tb_Messages, values,
                DatabaseHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Message> getAllRecords() {
        List<Message> messages = new ArrayList<Message>();

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String newMonth = String.format("%02d", mMonth+1);
        String newDay = String.format("%02d", mDay);
        String currentDay = String.valueOf(mYear) +
                String.valueOf(newMonth) + String.valueOf(newDay);

        Cursor cursor = database.query(DatabaseHelper.Glb_Tb_Messages,
                allColumns, " Glb_Tb_Messages.xDateFrom >= " + currentDay, null, null, null
                , " Glb_Tb_Messages.xDateFrom DESC ");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message record = cursorToMessage(cursor);
            messages.add(record);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }
    public List<Message> getBaseHistory() {
        List<Message> messages = new ArrayList<Message>();

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String newMonth = String.format("%02d", mMonth+1);
        String newDay = String.format("%02d", mDay);
        String currentDay = String.valueOf(mYear) +
                String.valueOf(newMonth) + String.valueOf(newDay);
        Cursor cursor = database.query(DatabaseHelper.Glb_Tb_Messages,
                allColumns, " Glb_Tb_Messages.xDateFrom <  " + currentDay, null, null, null, " Glb_Tb_Messages.xDateFrom DESC ");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message record = cursorToMessage(cursor);
            messages.add(record);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return messages;
    }

    public int getLastId() {
        int Int_LastId = 0;
        Cursor cursor = database
                .rawQuery(
                        "SELECT * FROM Glb_Tb_Messages ORDER BY _id DESC LIMIT 1;",
                        null);
        cursor.moveToLast();
        if (cursor.getCount() > 0) {
            Message record = cursorToMessage(cursor);
            Int_LastId = record.getId();
        }
        return Int_LastId;
    }

    private Message cursorToMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getInt(0));
        message.setTitle(cursor.getString(1));
        message.setDateFrom(cursor.getString(2));
        message.setDateTo(cursor.getString(3));
        message.setTimeFrom(cursor.getString(4));
        message.setTimeTo(cursor.getString(5));
        message.setType(cursor.getInt(6));
        message.setRepeatType(cursor.getString(7));
        message.setNotificationIntervals(cursor.getString(8));
        message.setIsImportant(cursor.getInt(9));
        return message;
    }
}
