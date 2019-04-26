package com.jetec.nordic_googleplay.NewActivity.UserSQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                "model" + " TEXT, " +
                "savelist" + " TEXT," +
                "numlist" + " TEXT" + ")";
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
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        return cursor.getCount();
    }

    public int modelsearch(String model){
        int count;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + table_name + " WHERE model=?", new String[]{model});

        count = cursor.getCount();
        Log.e("myLog","count =" + count);
        return count;
    }
}
