package com.example.connectionchecker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class DBManager {
    private Context context;
    private DataBaseSites db;
    private SQLiteDatabase sqlDb;
    private String table_Name;
    private String title;

    public DBManager(Context context,String table_Name,String dbName,String title)
    {
        this.table_Name = table_Name;
        this.context = context;
        this.title = title;
        db = new DataBaseSites(context,table_Name,dbName,title);
    }

    public void openDB()
    {
        sqlDb = db.getWritableDatabase();
    }

    public void addToDB(String site)
    {
        ContentValues vals = new ContentValues();
        vals.put(title,site);
        sqlDb.insert(table_Name,null,vals);
    }
    public void removeDb()
    {
        sqlDb.delete(table_Name,null,null);
    }

    public Vector<String> getDB()
    {
        Vector<String> list = new Vector<>();
        Cursor cursor = sqlDb.query(table_Name,null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            String site = cursor.getString(cursor.getColumnIndex(title));
            list.add(site);
        }
        cursor.close();
        return list;
    }

    public void closeDb()
    {
        db.close();
    }


}
