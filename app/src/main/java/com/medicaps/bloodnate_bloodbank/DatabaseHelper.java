package com.medicaps.bloodnate_bloodbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String BG_DATABASE = "BG_DATABASE";
    public static final String COLUMN_BG = "BLOOD_GROUP";
    public static final String COLUMN_AVAILABLE = "AVAILABLE";
    public static final String COLUMN_UNCLEARED = "UNCLEARED";

    public DatabaseHelper(@Nullable Context context){
        super(context,"BGDatabase.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "+BG_DATABASE+ " ("+COLUMN_BG+" TEXT PRIMARY KEY AUTOINCREMENT, "+COLUMN_AVAILABLE+" INTEGER, "+COLUMN_UNCLEARED+" INTEGER)";
        db.execSQL(createTableStatement);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
