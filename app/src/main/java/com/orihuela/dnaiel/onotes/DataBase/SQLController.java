package com.orihuela.dnaiel.onotes.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class SQLController {

    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController openDatabases() throws SQLException{
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void closeDatabases() {
        dbhelper.close();
    }

    public void insertData(String name, String note, int notif) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.NOTE_TITLE, name);
        cv.put(DBhelper.NOTE_NOTE, note);
        cv.put(DBhelper.NOTE_NOTIF, notif);
        database.insert(DBhelper.TABLE_NOTE, null, cv);
    }

    public Cursor readData() {
        String[] allTheCols = new String [] {
                DBhelper.NOTE_ID,DBhelper.NOTE_TITLE ,DBhelper.NOTE_NOTE, DBhelper.NOTE_NOTIF};
        Cursor c = database.query(DBhelper.TABLE_NOTE, allTheCols, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public int updateData(long noteID,String title, String note) {
        ContentValues cvUpgrade = new ContentValues();
        cvUpgrade.put(DBhelper.NOTE_TITLE, title);
        cvUpgrade.put(DBhelper.NOTE_NOTE, note);
        int i = database.update(DBhelper.TABLE_NOTE, cvUpgrade,
                DBhelper.NOTE_ID + " = " + noteID, null);
        return i;
    }

    public void deleteData(long noteID) {
        database.delete(DBhelper.TABLE_NOTE, DBhelper.NOTE_ID + "=" + noteID, null);
    }

    public void deleteDatabase(){
        database.delete(DBhelper.TABLE_NOTE, null, null);
    }

}
