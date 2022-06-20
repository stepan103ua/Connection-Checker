package com.example.connectionchecker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseSites extends SQLiteOpenHelper {
    public String TABLE_NAME = "my_table";
    public String _ID = "_id";
    public String TITLE = "title";
    //public static final String DISC = "disc";
    public String DB_NAME = "sites.db";
    public static int VERSION = 1;
    public String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT)";
    public String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DataBaseSites(@Nullable Context context,String TABLE_NAME, String DB_NAME,String title) {
        super(context, DB_NAME, null, VERSION);
        this.TABLE_NAME = TABLE_NAME;
        this.DB_NAME = DB_NAME;
        this.TITLE = title;
        TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT)";
        DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
