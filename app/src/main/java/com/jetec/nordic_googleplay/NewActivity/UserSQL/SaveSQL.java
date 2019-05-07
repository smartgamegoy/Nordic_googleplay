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
import java.util.HashMap;

public class SaveSQL extends SQLiteOpenHelper {

    private String TAG = "SaveSQL";
    private final static String table_name = "newdata"; //資料表名
    private final static String db_name = "datasql.db";    //資料庫名
    private static final int VERSION = 2;

    public SaveSQL(Context context) {
        super(context, db_name, null, VERSION);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_name, null, null, null, null,
                null, null, null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   //ok
        String DATABASE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + table_name + "(" +
                "_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL," +
                "model" + " TEXT, " +   //型號
                "name" + " TEXT, " +    //名稱
                "savelist" + " TEXT," + //設定值
                "numlist" + " TEXT" + ")";  //排數
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

    public int getCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        return cursor.getCount();
    }

    public int modelsearch(String model){
        int count;

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor=db.rawQuery("SELECT * FROM " + table_name + " WHERE model=?", new String[]{model});

        count = cursor.getCount();
        Log.e(TAG,"count =" + count);
        return count;
    }

    public ArrayList<HashMap<String, String>> fillList(String model){

        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor=db.rawQuery("SELECT * FROM " + table_name + " WHERE model=?", new String[]{model});
        Log.e("cursor","cursor.getcount = " + cursor.getCount());
        cursor.moveToFirst();
        do {
            String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("_id")));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String savelist = cursor.getString(cursor.getColumnIndex("savelist"));
            String numlist = cursor.getString(cursor.getColumnIndex("numlist"));

            HashMap<String, String> map = new HashMap<>();
            Log.e("putmap", "id" + id);
            map.put("id", id);
            map.put("name", name);
            map.put("savelist", savelist);
            map.put("numlist", numlist);

            dataList.add(map);
        }while(cursor.moveToNext());
        return dataList;
    }

    public int getCount(String name, String model){
        int count;

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor=db.rawQuery("SELECT * FROM " + table_name + " WHERE name=? AND model=?", new String[]{name, model});

        count = cursor.getCount();
        Log.e(TAG,"count =" + count);
        return count;
    }

    public void update(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);

        db.update(table_name, cv, "_id=" + id, null);
    }

    public void insert(JSONArray SQLjson, JSONArray Recordjson, String name, String model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("model", model);
        cv.put("name", name);
        cv.put("savelist", SQLjson.toString());
        cv.put("numlist", Recordjson.toString());

        long new_insert = db.insert(table_name, model, cv);
        Log.e(TAG,"SQL = " + new_insert);
        //return new_insert;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, "_id=" + id ,null);
    }
}
