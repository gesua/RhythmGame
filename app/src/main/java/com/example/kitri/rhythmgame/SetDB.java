package com.example.kitri.rhythmgame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SetDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "bookmark.db";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    public SetDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getReadableDatabase();
//        db = getWritableDatabase(); //비슷한거 별차이없음
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS spd(" +
                "num VARCHAR(30) NOT NULL UNIQUE " +
                ")";


        db.execSQL(sql);

        String sqll="INSERT INTO spd VALUES('25')";
        db.execSQL(sqll);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS spd";
        db.execSQL(sql);
        onCreate(db);

    }

    public String select() {
        String result = "";
        String sql = "SELECT num FROM spd";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToNext()) {

            result = cursor.getString(0);

        }
        return result;
    }

    public void update(String num) { //
        String sql2 = "UPDATE spd SET num ='" + num + "'";
        db.execSQL(sql2);
    }
}

