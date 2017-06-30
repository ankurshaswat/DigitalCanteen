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
        String query = "CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Name TEXT,Balance DOUBLE,Status TEXT,UID TEXT)";
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
        String query = "CREATE TABLE IF NOT EXISTS Users(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Name TEXT,Balance DOUBLE,Status TEXT,UID TEXT)";
        db.execSQL(query);
    }

    public Cursor checkEmployeeId(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor results = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
        if (results.moveToFirst()) {
            return results;
        } else {
            results.close();
            return db.rawQuery("SELECT * FROM Users WHERE UID=?", new String[]{employee_id});

        }
    }

    public boolean insertUser(String employee_id, String name, Double balance, String UID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertUser: writing to new content");
        new_content.put("Employee_code", employee_id);
//        new_content.put("Name", employee_name);
        new_content.put("Balance", balance);
        new_content.put("Name", name);
        new_content.put("UID", UID);

        new_content.put("Status", String.valueOf(Status.NEW));

        Log.d(TAG, "insertUser: inseting to db");
        long result = db.insert("Users", null, new_content);

        return result != -1;
    }

    public double getBal(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
        cur.moveToFirst();
        Double balance = cur.getDouble(3);
        cur.close();
        return balance;
    }


    public String getUID(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
        cur.moveToFirst();
        String UID = cur.getString(5);
        cur.close();
        return UID;
    }

    public String getName(String employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
        cur.moveToFirst();
        String name = cur.getString(2);
        cur.close();
        return name;

    }

    public boolean updateinfo(String employee_id, Double amt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        amt = amt + getBal(employee_id);
        Log.d(TAG, "updateinfo: " + amt);
        newValues.put("Balance", amt);
        newValues.put("Status", String.valueOf(Status.NEW));
        newValues.put("Employee_code", employee_id);

        Log.d(TAG, "updateinfo: " + getBal(employee_id));

//        String[] args = new String[]{employee_id};

        long result = db.update("Users", newValues, "Employee_code=?", new String[]{employee_id});
        return result != -1;


    }

    public boolean updateUID(String employee_id, String UID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();


        newValues.put("UID", UID);
        newValues.put("Status", String.valueOf(Status.NEW));
        newValues.put("Employee_code", employee_id);

//        Log.d(TAG, "updateinfo: " + getBal(employee_id));

//        String[] args = new String[]{employee_id};

        long result = db.update("Users", newValues, "Employee_code=?", new String[]{employee_id});
        return result != -1;


    }


    public List<Employee> getAll() {

        List<Employee> empHis = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users", null);
        while (cur.moveToNext()) {
//            Integer iid = cur.getInt(0);
//            String st1 = cur.getString(1);
//            Double st2 = cur.getDouble(2);
//            itemlist.add(new menuItem(st1, st2 + "", iid));
            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Double bal = cur.getDouble(3);
            String UID = cur.getString(5);

//            Date date_ = null;
//            try {
//                date_ = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            empHis.add(new Employee(emp_code, name, bal, cur.getInt(0), UID));

        }
        cur.close();
        Log.d(TAG, "getAllHistory: " + empHis.size());
        return empHis;

    }

    public List<Employee> get(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Status=?", new String[]{String.valueOf(status)});

        List<Employee> newItems = new ArrayList<>();
        while (cur.moveToNext()) {
            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Double bal = cur.getDouble(3);
            String UID = cur.getString(5);
            newItems.add(new Employee(emp_code, name, bal, cur.getInt(0), UID));
        }
        cur.close();
        return newItems;
    }

    public boolean updateStatus(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("Status", String.valueOf(Status.SYNCED));

        String[] args = new String[]{String.valueOf(id)};
        long result = db.update("Users", newValues, "ID=?", args);
        return result != -1;
    }

    public boolean isSynced() {
        return get(Status.NEW).size() == 0;
    }

    public boolean insertUserUpdated(String employee_id, String name, Double balance, String UID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertUser: writing to new content");
        new_content.put("Employee_code", employee_id);
//        new_content.put("Name", employee_name);
        new_content.put("Balance", balance);
        new_content.put("Name", name);
        new_content.put("UID", UID);
        new_content.put("Status", String.valueOf(Status.SYNCED));

        Log.d(TAG, "insertUser: inseting to db");
        long result = db.insert("Users", null, new_content);

        return result != -1;
    }

    public void del() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Users", null, null);
    }


    public enum Status {
        NEW,
        SYNCED
    }
}
