package com.example.digitalcanteen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.digitalcanteen.dataObjects.Employee;

import java.util.ArrayList;
import java.util.List;

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
 *
 * 2: Balance
 * 3: Date
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
        String query = "CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Name TEXT,Balance DOUBLE)";
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
        String query = "CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Name TEXT,Balance DOUBLE)";
        db.execSQL(query);
    }

    public Cursor checkEmployeeId(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});

    }

    public boolean insertUser(String employee_id, String name, Double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertUser: writing to new content");
        new_content.put("Employee_code", employee_id);
//        new_content.put("Name", employee_name);
        new_content.put("Balance", balance);
        new_content.put("Name", name);
        Log.d(TAG, "insertUser: inseting to db");
        long result = db.insert("Users", null, new_content);

        return result != -1;
    }

    public double getBal(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
        cur.moveToFirst();
        return cur.getDouble(3);

    }

    public String getName(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
        cur.moveToFirst();
        return cur.getString(2);

    }

    public boolean updateinfo(String employee_id, Double amt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        amt = amt + getBal(employee_id);
        Log.d(TAG, "updateinfo: " + amt);

        newValues.put("Balance", amt);

        newValues.put("Employee_code", employee_id);

        Log.d(TAG, "updateinfo: " + getBal(employee_id));

//        String[] args = new String[]{employee_id};

        long result = db.update("Users", newValues, "Employee_code=?", new String[]{employee_id});
        return result != -1;


    }

    public List<Employee> getAll() {

        List<Employee> empHis = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        //TODO change this query for dates
        Cursor cur = db.rawQuery("SELECT * FROM Users", null);
        while (cur.moveToNext()) {
//            Integer iid = cur.getInt(0);
//            String st1 = cur.getString(1);
//            Double st2 = cur.getDouble(2);
//            itemlist.add(new menuItem(st1, st2 + "", iid));
            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Double bal = cur.getDouble(3);


//            Date date_ = null;
//            try {
//                date_ = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            empHis.add(new Employee(emp_code, name, bal, cur.getInt(0)));

        }
        cur.close();
        Log.d(TAG, "getAllHistory: " + empHis.size());
        return empHis;

    }

}
