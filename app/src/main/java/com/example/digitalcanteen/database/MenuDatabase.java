package com.example.digitalcanteen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.digitalcanteen.dataObjects.menuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankurshaswat on 27/5/17.
 */

public class MenuDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Menu.db";
    private static final String TAG = "MenuDatabase";
    public MenuDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating db");
        String query = "CREATE TABLE IF NOT EXISTS Menu(ID Integer PRIMARY KEY AUTOINCREMENT,Item_name TEXT,Cost DOUBLE,Status TEXT)";
        db.execSQL(query);
        Log.d(TAG, "onCreate: db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Menu");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS Menu(ID Integer PRIMARY KEY AUTOINCREMENT,Item_name TEXT,Cost DOUBLE,Status TEXT)";
        db.execSQL(query);
    }

    public boolean insertItem(String Item_name, double Cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertItem: writing to new content");

        new_content.put("Item_name", Item_name);
        new_content.put("Cost", Cost);
        new_content.put("Status", String.valueOf(Status.NEW));

        Log.d(TAG, "insertItem: inseting to db");
        long result = db.insert("Menu", null, new_content);

        return result != -1;
    }
//
//    public Cursor checkEmployeeId(String employee_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        return db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
//
//    }

    public List<menuItem> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Menu WHERE NOT Status=?", new String[]{String.valueOf(Status.DELETED)});

        List<menuItem> itemlist = new ArrayList<>();
        while (cur.moveToNext()) {
            Integer iid = cur.getInt(0);
            String st1 = cur.getString(1);
            Double st2 = cur.getDouble(2);
            itemlist.add(new menuItem(st1, st2 + "", iid));
        }
        cur.close();
        Log.d(TAG, "getAll: " + itemlist.size());
        return itemlist;
    }

    public int getItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cur = db.rawQuery("SELECT * FROM Menu WHERE Item_name=?", new String[]{name});
        Cursor cur = db.rawQuery("SELECT * FROM Menu WHERE Item_name=? AND NOT Status=?", new String[]{name, String.valueOf(Status.DELETED)});
        cur.moveToFirst();
//        Log.d(TAG, "getItem:toloooooooooooo      "+ cur.getInt(0)+"   "+cur.getString(1));
        Integer itemid = cur.getInt(0);
        cur.close();
        return itemid;
    }

    public Double getItemPrice(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cur = db.rawQuery("SELECT * FROM Menu WHERE Item_name=?", new String[]{name});
        Cursor cur = db.rawQuery("SELECT * FROM Menu WHERE Item_name=? AND NOT Status=?", new String[]{name, String.valueOf(Status.DELETED)});
        if (cur.moveToFirst())
//        Log.d(TAG, "getItem:toloooooooooooo      "+ cur.getInt(0)+"   "+cur.getString(1));
        {
            Double itemPrice = cur.getDouble(2);
            cur.close();
            return itemPrice;
        } else {
            cur.close();
            return Double.valueOf(-1);
        }
    }

    public boolean editItem(int id, String item_name, double Cost) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues newValues = new ContentValues();
        newValues.put("Cost", Cost);
        newValues.put("Item_name", item_name);
        newValues.put("Status", String.valueOf(Status.NEW));

        String[] args = new String[]{String.valueOf(id)};
        long result = db.update("Menu", newValues, "ID=?", args);
        return result != -1;


    }

    public boolean deleteItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues newValues = new ContentValues();
        newValues.put("Status", String.valueOf(Status.DELETED));

        String[] args = new String[]{name};
        long result = db.update("Menu", newValues, "Item_name=?", args);
        return result != -1;
    }

    public boolean finalDelete(int id) {
        int res;
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.delete("Menu", "ID=?", new String[]{String.valueOf(id)});
        return (res != 0);
    }

    public List<menuItem> get(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Menu WHERE Status=?", new String[]{String.valueOf(status)});

        List<menuItem> newItems = new ArrayList<>();
        while (cur.moveToNext()) {
            Integer iid = cur.getInt(0);
            String Item_name = cur.getString(1);
            Double Cost = cur.getDouble(2);
            newItems.add(new menuItem(Item_name, String.valueOf(Cost), iid));
        }
        cur.close();
        return newItems;
    }

    public boolean updateStatus(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("Status", String.valueOf(Status.SYNCED));

        String[] args = new String[]{String.valueOf(id)};
        long result = db.update("Menu", newValues, "ID=?", args);
        return result != -1;
    }

    public boolean isSynced() {
        return (get(MenuDatabase.Status.NEW).size() == 0 & get(Status.DELETED).size() == 0);
    }

    public boolean insertMenuUpdated(String Item_name, Double Cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertItem: writing to new content");

        new_content.put("Item_name", Item_name);
        new_content.put("Cost", Cost);
        new_content.put("Status", String.valueOf(Status.SYNCED));

        Log.d(TAG, "insertItem: inseting to db");
        long result = db.insert("Menu", null, new_content);

        return result != -1;
    }

    public void del() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Menu", null, null);
    }
    public enum Status {
        NEW,
        SYNCED,
        DELETED
    }

}
