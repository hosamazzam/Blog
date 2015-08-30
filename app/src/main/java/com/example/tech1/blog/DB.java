package com.example.tech1.blog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by tech 1 on 7/6/2015.
 */
public class DB extends SQLiteOpenHelper {
    String DATABASE_TABLE;

    public DB(Context context) {
        super(context, "Blog.db", null, 1);
    }

    public String getDATABASE_TABLE() {
        return DATABASE_TABLE;
    }

    public void setDATABASE_TABLE(String DATABASE_TABLE) {
        this.DATABASE_TABLE = DATABASE_TABLE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table private_note (id integer primary key autoincrement, title text,des text,imagepath text)"
        );
        db.execSQL(
                "create table  private_alert (id integer primary key autoincrement, title text,time text,date text,switch text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }

    public boolean insert_note(String table, String title, String des, String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("des", des);
        contentValues.put("imagepath", path);
        db.insert(table, null, contentValues);
        db.close();
        return true;
    }
    public boolean insert_alert(String table, String title, String time, String date,String turn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("switch", turn);
        db.insert(table, null, contentValues);
        db.close();
        return true;
    }

    public boolean updatenote(Integer id, String table, String title, String des, String path)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("des", des);
        contentValues.put("imagepath", path);
        db.update(table, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }
    public boolean updatealert(Integer id, String table, String title, String time, String date,String turn)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("switch", turn);
        db.update(table, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }

    public Integer delete(String table, Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table,
                "id = ? ",
                new String[] { Integer.toString(id) });

    }


    public ArrayList<String> getAllNote(String table)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + table, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex("id")));
                array_list.add(res.getString(res.getColumnIndex("title")));
                array_list.add(res.getString(res.getColumnIndex("des")));
                array_list.add(res.getString(res.getColumnIndex("imagepath")));
                res.moveToNext();
            }
            db.close();
        }
        catch (Exception e){
            System.out.println("table not found");
            return array_list;
        }
        return array_list;
    }
    public ArrayList<String> getAllAlerts(String table)
    {
        ArrayList<String> array_list = new ArrayList<String>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + table, null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex("id")));
                array_list.add(res.getString(res.getColumnIndex("title")));
                array_list.add(res.getString(res.getColumnIndex("time")));
                array_list.add(res.getString(res.getColumnIndex("date")));
                array_list.add(res.getString(res.getColumnIndex("switch")));
                res.moveToNext();
            }
            db.close();
        }
        catch (Exception e){
            System.out.println("table not found");
            return array_list;
        }
        return array_list;
    }
    public void createTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "create table if not exists private_note (id integer primary key autoincrement, title text,des text,imagepath text)"
        );
        db.execSQL(
                "create table if not exists private_alert (id integer primary key autoincrement, title text,time text,date text,switch text)"
        );
        db.close();
    }
}
