package com.example.digitalcanteen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.digitalcanteen.dataObjects.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankurshaswat on 22/6/17.
 */

public class CollectionDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Collections.db";
    private static final String TAG = "Collections";

    public CollectionDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating db");
        String query = "CREATE TABLE IF NOT EXISTS Collections(ID Integer PRIMARY KEY AUTOINCREMENT,Date TEXT,Collection DOUBLE,Status TEXT)";
        db.execSQL(query);
        Log.d(TAG, "onCreate: db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Collections");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS Collections(ID Integer PRIMARY KEY AUTOINCREMENT,Date TEXT,Collection DOUBLE,Status TEXT)";
        db.execSQL(query);
    }

    public boolean checkCollection(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM Collections WHERE Date=?", new String[]{date});
        cur.moveToFirst();
        return cur.getInt(0) > 0;

    }

    public boolean insertItem(String Date, Double Collection) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertItem: writing to new content");

        new_content.put("Date", Date);
        new_content.put("Collection", Collection);
        new_content.put("Status", String.valueOf(Status.NEW));

        Log.d(TAG, "insertItem: inseting to db");
        long result = db.insert("Collections", null, new_content);

        return result != -1;
    }
//
//    public Cursor checkEmployeeId(String employee_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        return db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
//
//    }

    public List<Collection> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Collections ORDER BY Date", null);

        List<Collection> itemlist = new ArrayList<>();
        while (cur.moveToNext()) {
            Integer iid = cur.getInt(0);
            String Date = cur.getString(1);
            Double col = cur.getDouble(2);
            itemlist.add(new Collection(Date, iid, col));
        }
        cur.close();
        Log.d(TAG, "getAll: " + itemlist.size());
        return itemlist;
    }

    public int getCollection(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cur = db.rawQuery("SELECT * FROM  WHERE Item_name=?", new String[]{name});
        Cursor cur = db.rawQuery("SELECT * FROM Collections WHERE Date=?", new String[]{date});
        cur.moveToFirst();
//        Log.d(TAG, "getItem:toloooooooooooo      "+ cur.getInt(0)+"   "+cur.getString(1));
        Integer Coll = cur.getInt(2);
        cur.close();
        return Coll;
    }

    public boolean addCollection(String Date, Double money) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("Status", String.valueOf(Status.NEW));
        newValues.put("Collection", getCollection(Date) + money);

        String[] args = new String[]{String.valueOf(Date)};
        long result = db.update("Collections", newValues, "Date=?", args);
        return result != -1;
    }

    public List<Collection> getAllHistory(String strtDate, String endDate) {

        List<Collection> empHis = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        //TODO change this query for dates
        Cursor cur = db.rawQuery("SELECT * FROM Transactions WHERE (Date BETWEEN ? AND ?)", new String[]{strtDate, endDate});
        while (cur.moveToNext()) {
//            Integer iid = cur.getInt(0);
//            String st1 = cur.getString(1);
//            Double st2 = cur.getDouble(2);
//            itemlist.add(new menuItem(st1, st2 + "", iid));
            Integer iid = cur.getInt(0);
            String Date = cur.getString(1);
            Double col = cur.getDouble(2);

            Log.d(TAG, "getAllHistory: setting date = " + Date);
//            Date date_ = null;
//            try {
//                date_ = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            empHis.add(new Collection(Date, iid, col));

        }
        cur.close();
        Log.d(TAG, "getAllHistory: " + empHis.size());
        return empHis;

    }


    public List<Collection> get(CollectionDatabase.Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Collections WHERE Status=?", new String[]{String.valueOf(status)});

        List<Collection> newItems = new ArrayList<>();
        while (cur.moveToNext()) {
            Integer iid = cur.getInt(0);
            String Date = cur.getString(1);
            Double col = cur.getDouble(2);
            newItems.add(new Collection(Date, iid, col));
        }
        cur.close();
        return newItems;
    }

    public boolean updateStatus(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("Status", String.valueOf(Status.SYNCED));

        String[] args = new String[]{String.valueOf(id)};
        long result = db.update("Collections", newValues, "ID=?", args);
        return result != -1;
    }


    public enum Status {
        NEW,
        SYNCED,
    }

}
