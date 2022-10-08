package com.medicaps.bloodnate_bloodbank;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.MediaSession;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
// RequestDatabaseHelper extending properties from SQLiteOpenHelper
public class RequestDatabaseHelper extends SQLiteOpenHelper {
    public static final String REQUEST_DATABASE = "REQ_DATABASE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TOKEN = "TOKEN";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_ADDRESS= "ADDRESS";
    public static final String COLUMN_REQUESTED_BG = "REQUESTED_BG";
    public static final String VOLUME = "VOLUME";
    public static final String COLUMN_DONOR_BG = "DONOR_BG";
    public static final String COLUMN_REQUEST_TYPE = "REQUEST_TYPE";
    //constructor calling super constructor
    public RequestDatabaseHelper(@Nullable Context context) {
        super(context, "RequestDatabase.db",null,1);
    }
    // overriding onCreate method to create a database/table if doesn't exists
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE "+REQUEST_DATABASE+"("+COLUMN_TOKEN+" TEXT, "+COLUMN_NAME+" TEXT, "+COLUMN_EMAIL+" TEXT, "+COLUMN_ADDRESS+" TEXT ,"+COLUMN_REQUEST_TYPE+" TEXT, "+COLUMN_REQUESTED_BG+" TEXT, "+VOLUME+" INTEGER, "+COLUMN_DONOR_BG+" TEXT)";
        db.execSQL(createTableStatement);
        if (db.isOpen()){
            System.out.println("Database is open...");
        }
    }
    // no change in onUpgrade because we are using only one version of database and we are not going to change it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // addRequest adds a request in the database and return true if success
    public boolean addRequest(Request request){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TOKEN,request.token);
        cv.put(COLUMN_NAME,request.name);
        cv.put(COLUMN_EMAIL,request.email);
        cv.put(COLUMN_ADDRESS,request.address);
        cv.put(COLUMN_REQUEST_TYPE,request.requestType.toString());
        cv.put(COLUMN_REQUESTED_BG,request.RequestBloodGroup!=null?BloodGroup.BG(request.RequestBloodGroup):"null");
        cv.put(VOLUME,request.volume);
        cv.put(COLUMN_DONOR_BG,request.DonorBloodGroup!=null?BloodGroup.BG(request.DonorBloodGroup):"null");
        return db.insert(REQUEST_DATABASE, null, cv) != -1;
    }
    // returns a list of all the requests present in the database
    public List<Request> getRequestList(){
        List<Request> requestList = new ArrayList<>();
        String query = "SELECT * FROM "+REQUEST_DATABASE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                String token = cursor.getString(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String address = cursor.getString(3);
                String type = cursor.getString(4);
                String reqBG = cursor.getString(5);
                int volume = cursor.getInt(6);
                String donBG = cursor.getString(7);
                Request request = new Request(token,name,email,address,RequestType.getRequestType(type),BloodGroup.BG(reqBG),volume,BloodGroup.BG(donBG));
                requestList.add(request);
            }while (cursor.moveToNext());
        }else {
            System.out.println("NO REQUESTS FOUND");
        }
        return requestList;
    }
    // delete a Request from database and return true if success
    public boolean deleteRequest(Request request){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+REQUEST_DATABASE+" WHERE "+COLUMN_TOKEN+" = "+"\""+request.token+"\"";
        Cursor cursor = db.rawQuery(query, null);
        return !cursor.moveToFirst();
    }
}
