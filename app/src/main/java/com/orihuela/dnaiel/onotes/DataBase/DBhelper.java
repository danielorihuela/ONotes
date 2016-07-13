package com.orihuela.dnaiel.onotes.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final String TABLE_NOTE = "tabla";
    public static final String NOTE_TITLE = "titulo";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_NOTE = "nota";
    public static final String NOTE_NOTIF = "notif";

    static final String DB_NAME = "DBTITLE";
    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table "
            + TABLE_NOTE + "(" + NOTE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOTE_TITLE + " TEXT NO NULL, "
            + NOTE_NOTIF + " INTEGER, "
            + NOTE_NOTE + " TEXT NO NULL);";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TITLE);
        onCreate(db);
    }
}
