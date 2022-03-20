package com.example.sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    //contructor
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // truy vấn không trả về kết quả: CREAT,INSERT UPDATE, DELETE...
    // tham số truyền vào là câu lệnh truy vấn
    public void QueryData(String sql){
        // đọc ghi data
        SQLiteDatabase database = getWritableDatabase();
        // thực thi
        database.execSQL(sql);
    }
    // truy vấn có trả về kết quả: SELECT
    public Cursor GetData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
