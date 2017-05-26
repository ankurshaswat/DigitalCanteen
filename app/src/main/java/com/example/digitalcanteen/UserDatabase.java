package com.example.digitalcanteen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ankurshaswat on 21/5/17.
 * <p>
 * This class will refer to balance of different users
 * and store their active balance
 *
 *
 * indexes are as follows
 * 0: ID
 * 1: Employee_id
 * 2: Name
 * 3: Balance
 * 4: Date
 */

//TODO :Extend this later to store history by creatinng another databse of histories

public class UserDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Users.db";
    private static final String TAG = "UserDatabase";


    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating db");
        String query = "CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Name TEXT,Balance REAL,Date TEXT)";
        db.execSQL(query);
        Log.d(TAG, "onCreate: db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Name TEXT,Balance REAL,Date TEXT)";
        db.execSQL(query);
    }

    public Cursor checkEmployeeId(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});

    }

    public boolean insertUser(String employee_id, String employee_name, double balance, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertUser: writing to new content");
        new_content.put("Employee_code", employee_id);
        new_content.put("Name", employee_name);
        new_content.put("Balance", balance);
        new_content.put("Date", date);
        Log.d(TAG, "insertUser: inseting to db");
        long result = db.insert("Users", null, new_content);

        return result != -1;
    }
}