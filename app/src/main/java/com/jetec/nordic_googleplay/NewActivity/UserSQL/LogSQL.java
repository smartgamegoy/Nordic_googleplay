package com.jetec.nordic_googleplay.NewActivity.UserSQL;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class LogSQL extends SQLiteOpenHelper {

    private String TAG = "LogSQL";
    private final static String table_name = "logdata"; //資料表名
    private final static String db_name = "logsql.db";    //資料庫名
    private static final int VERSION = 2;

    public LogSQL(Context context) {
        super(context, db_name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   //ok
        String DATABASE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + table_name + "(" +
                "_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL," +
                "name" + " TEXT, " +   //名稱
                "timelist" + " TEXT, " +    //時間
                "savelist" + " TEXT" + ")"; //list資料
        db.execSQL(DATABASE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //oldVersion=舊的資料庫版本；newVersion=新的資料庫版本
        //db.execSQL("DROP TABLE IF EXISTS " + table_name); //刪除舊有的資料表
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // TODO 每次成功打開數據庫後首先被執行
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public void insert(JSONArray timelist, JSONArray savelist, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Log.e(TAG, "timelist = " + timelist);
        Log.e(TAG, "savelist = " + savelist);
        Log.e(TAG, "name = " + name);
        cv.put("name", name);
        cv.put("timelist", timelist.toString());
        cv.put("savelist", savelist.toString());

        db.insert(table_name, name, cv);
    }

    public int getCount(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor=db.rawQuery("SELECT * FROM " + table_name + " WHERE name=?", new String[]{name});
        return cursor.getCount();
    }

    public int getCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        return cursor.getCount();
    }

    public void delete(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] del = new String[] {name};
        //db.delete(table_name, "address" + "=?",del);
        db.delete(table_name, "name" + "=?" ,del);
    }

    public List<String> getjson(String name){
        List<String> jsonlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE name = ?", new String[] {name});

        cursor.moveToFirst();
        String listname = cursor.getString(cursor.getColumnIndex("name"));
        String time = cursor.getString(cursor.getColumnIndex("timelist"));
        String save = cursor.getString(cursor.getColumnIndex("savelist"));

        jsonlist.add(listname);
        jsonlist.add(time);
        jsonlist.add(save);

        return jsonlist;
    }
}
