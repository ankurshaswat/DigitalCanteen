package com.example.digitalcanteen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.digitalcanteen.dataObjects.EHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankurshaswat on 30/5/17.
 */

public class TransactionDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Transactions.db";
    private static final String TAG = "TransactionDatabase";
    public TransactionDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating db");
        String query = "CREATE TABLE IF NOT EXISTS Transactions(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Order_name TEXT,Quantity Integer,Cost_perItem DOUBLE,Total DOUBLE,Date TEXT,Status TEXT,num Integer)";
        db.execSQL(query);
        Log.d(TAG, "onCreate: db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS Transactions(ID Integer PRIMARY KEY AUTOINCREMENT,Employee_code TEXT,Order_name TEXT,Quantity Integer,Cost_perItem DOUBLE,Total DOUBLE,Date TEXT,Status TEXT,num Integer)";
        db.execSQL(query);
    }

    public boolean insertTransaction(String Employee_id, String Order_name, Integer Quantity, Double cpi, String date, Integer num) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();

        Log.d(TAG, "insertTransaction: checking enum " + String.valueOf(Status.NEW));
        Log.d(TAG, "insertUser: writing to new content");

//        new_content.put("Name", employee_name);
        new_content.put("Employee_code", Employee_id);
        new_content.put("Order_name", Order_name);
        new_content.put("Quantity", Quantity);
        new_content.put("Cost_perItem", cpi);
        new_content.put("Date", date);
        new_content.put("Total", Quantity * cpi);
        new_content.put("num", num);
        new_content.put("Status", String.valueOf(Status.NEW));

        Log.d(TAG, "insertUser: inseting to db");
        long result = db.insert("Transactions", null, new_content);

        return result != -1;
    }

//    public Cursor getEmployeeHistory(String employee_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.rawQuery("SELECT * FROM Transactions WHERE Employee_code=?", new String[]{employee_id});
//    }

    public Integer numCustomers(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(DISTINCT Employee_code) FROM Transactions WHERE Date=?", new String[]{date});
        cur.moveToFirst();
        Integer numCustomers = cur.getInt(0);
        cur.close();
        return numCustomers;


    }

    public Integer getNextNum() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*),MAX(num) FROM Transactions", null);
        cur.moveToFirst();
        if (cur.getInt(0) == 0) {
            cur.close();
            return 1;
        }
        Integer num = cur.getInt(1);
        cur.close();
        return num + 1;
    }

    public List<EHistory> getEmpHist(String employee_id) {

        List<EHistory> empHis = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        //TODO change this query for dates
        Cursor cur = db.rawQuery("SELECT * FROM Transactions WHERE Employee_code=?", new String[]{employee_id});
        while (cur.moveToNext()) {
//            Integer iid = cur.getInt(0);
//            String st1 = cur.getString(1);
//            Double st2 = cur.getDouble(2);
//            itemlist.add(new menuItem(st1, st2 + "", iid));

            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Integer quan = cur.getInt(3);
            Double cpi = cur.getDouble(4);
            Double tot = cur.getDouble(5);
            String date = cur.getString(6);

//            Log.d(TAG, "getEmpHist: setting date ="+ date  );
//            Date date_ = null;
//            try {
//                date_ = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            empHis.add(new EHistory(name, cpi, quan, date, cur.getInt(0), emp_code, tot, cur.getInt(8)));

        }
        cur.close();
        Log.d(TAG, "getAll: " + empHis.size());
        return empHis;

    }

    public List<EHistory> getAllHistory(String strtDate, String endDate) {

        List<EHistory> empHis = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        //TODO change this query for dates
        Cursor cur = db.rawQuery("SELECT * FROM Transactions WHERE (Date BETWEEN ? AND ?)", new String[]{strtDate, endDate});
        while (cur.moveToNext()) {
//            Integer iid = cur.getInt(0);
//            String st1 = cur.getString(1);
//            Double st2 = cur.getDouble(2);
//            itemlist.add(new menuItem(st1, st2 + "", iid));
            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Integer quan = cur.getInt(3);
            Double cpi = cur.getDouble(4);
            Double tot = cur.getDouble(5);
            String date = cur.getString(6);

            Log.d(TAG, "getAllHistory: setting date = " + date);
//            Date date_ = null;
//            try {
//                date_ = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            empHis.add(new EHistory(name, cpi, quan, date, cur.getInt(0), emp_code, tot, cur.getInt(8)));

        }
        cur.close();
        Log.d(TAG, "getAllHistory: " + empHis.size());
        return empHis;

    }

    public Cursor getAllHistoryCursor(String strtDate, String endDate) {

        SQLiteDatabase db = this.getWritableDatabase();


        return db.rawQuery("SELECT * FROM Transactions WHERE (Date BETWEEN ? AND ?)", new String[]{strtDate, endDate});

    }

    public List<EHistory> getAll() {

        List<EHistory> empHis = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        //TODO change this query for dates
        Cursor cur = db.rawQuery("SELECT * FROM Transactions", new String[]{});
//        Log.d(TAG, "getAll: "+cur.moveToFirst());
        while (cur.moveToNext()) {
//            Log.d(TAG, "getAll: im in");
//            Integer iid = cur.getInt(0);
//            String st1 = cur.getString(1);
//            Double st2 = cur.getDouble(2);
//            itemlist.add(new menuItem(st1, st2 + "", iid));
            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Integer quan = cur.getInt(3);
            Double tot = cur.getDouble(5);

            Double cpi = cur.getDouble(4);
            String date = cur.getString(6);
//            Date date_ = null;
//            try {
//                date_ = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            Log.d(TAG, "getAll: "+date);
//            Log.d(TAG, "getAll: "+date_);


            empHis.add(new EHistory(name, cpi, quan, date, cur.getInt(0), emp_code, tot, cur.getInt(8)));

        }
        cur.close();
//        Log.d(TAG, "getAll: " + empHis.size());
        return empHis;

    }

    public List<EHistory> get(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Transactions WHERE Status=?", new String[]{String.valueOf(status)});

        List<EHistory> newItems = new ArrayList<>();
        while (cur.moveToNext()) {
            String emp_code = cur.getString(1);
            String name = cur.getString(2);
            Integer quan = cur.getInt(3);
            Double tot = cur.getDouble(5);

            Double cpi = cur.getDouble(4);
            String date = cur.getString(6);
            newItems.add(new EHistory(name, cpi, quan, date, cur.getInt(0), emp_code, tot, cur.getInt(8)));
        }
        cur.close();
        return newItems;
    }

    public boolean updateStatus(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("Status", String.valueOf(Status.SYNCED));

        String[] args = new String[]{String.valueOf(id)};
        long result = db.update("Transactions", newValues, "ID=?", args);
        return result != -1;
    }

    public enum Status {
        NEW,
        SYNCED,
    }
    //    public double getBal(String employee_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cur = db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
//        return cur.getDouble(2);
//    }

//    public boolean updateinfo(String employee_id, double amt) {
//
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        ContentValues newValues = new ContentValues();
//        newValues.put("Balance", amt + getBal(employee_id));
//
//        String[] args = new String[]{employee_id};
//        long result = db.update("Users", newValues, "Employee_code=?", args);
//        return result != -1;
//    }


}
