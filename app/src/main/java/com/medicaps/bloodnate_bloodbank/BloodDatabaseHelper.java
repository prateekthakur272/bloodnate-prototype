package com.medicaps.bloodnate_bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.PublicKey;
//BloodDatabaseHelper extending properties from SQLiteOpenHelper, this class is inbuilt in the android sdk
public class BloodDatabaseHelper extends SQLiteOpenHelper {
    // column names
    public static final String BG_DATABASE = "BG_DATABASE";
    public static final String COLUMN_BG = "BLOOD_GROUP";
    public static final String COLUMN_AVAILABLE = "AVAILABLE";
    public static final String COLUMN_UNCLEARED = "UNCLEARED";
    // BloodDatabaseHelper calling constructor of SQLiteOpenHelper
    public BloodDatabaseHelper(@Nullable Context context) {
        super(context,"BGDatabase.db",null,1);
    }
    // override oncreate method to our BloodDatabaseHelper class
    @Override
    public void onCreate(SQLiteDatabase db) {
        // sql query to create a table in database BGdatabase
        String query = "CREATE TABLE "+BG_DATABASE+"("+COLUMN_BG+" TEXT, "+COLUMN_AVAILABLE+" INTEGER, "+COLUMN_UNCLEARED+" INTEGER )";
        db.execSQL(query);
        // checking whether table created or not
        if (db.isOpen())
            System.out.println("Database loaded...");
    }
    // no change in the onUpgrade because we are not going to change the version of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // addDAta method to add an entry in the database
    // but it is not used anywhere because we are not going to add entries other than our blood groups
    private boolean addData(String valueBG,int avail,int uncleared){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BG,valueBG);
        cv.put(COLUMN_AVAILABLE,avail);
        cv.put(COLUMN_UNCLEARED,uncleared);
        return db.insert(BG_DATABASE,null,cv) != -1;
    }
    // updates value at AVAILABLE column in the table
    public boolean updateValueAvail(String bg,int avail){
        initial();
        String query = "UPDATE "+BG_DATABASE+" SET "+COLUMN_AVAILABLE+" = "+avail+" WHERE "+COLUMN_BG+" = "+"\""+bg+"\"";
        System.out.println(query);
        SQLiteDatabase db = this.getWritableDatabase();
        try{db.execSQL(query);}catch (Exception e){return false;}
        return true;
    }
    // updates value at UNCLEARED column in the table
    public boolean updateValueUncleared(String bg,int uncleared){
        initial();
        String query = "UPDATE "+BG_DATABASE+" SET "+COLUMN_UNCLEARED+" = "+uncleared+" WHERE "+COLUMN_BG+" = "+"\""+bg+"\"";
        System.out.println(query);
        SQLiteDatabase db = this.getWritableDatabase();
        try{db.execSQL(query);}catch (Exception e){return false;}
        return true;
    }
    // returns the value at column AVAILABLE at some BLOOD_GROUP
    public int getAvail(String bg){
        initial();
        String query = "SELECT "+COLUMN_AVAILABLE+" FROM "+BG_DATABASE+" WHERE "+COLUMN_BG+" = \""+bg+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }else {
            System.out.println("No record found");
            return 0;
        }
    }
    // returns the value at column UNCLEARED at some BLOOD_GROUP
    public int getUnclear(String bg) {
        initial();
        String query = "SELECT " + COLUMN_UNCLEARED + " FROM " + BG_DATABASE + " WHERE " + COLUMN_BG + " = \"" + bg + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            System.out.println("No record found");
            return 0;
        }
    }
    // initialize the database for the first time if empty
    private void initial(){
        String query = "SELECT * FROM "+BG_DATABASE;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount()==0){
            this.addData("A+",0,0);
            this.addData("B+",0,0);
            this.addData("O+",0,0);
            this.addData("AB+",0,0);
            this.addData("A-",0,0);
            this.addData("B-",0,0);
            this.addData("O-",0,0);
            this.addData("AB-",0,0);
            System.out.println("Database initialised");
        }
    }
}
