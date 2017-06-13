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

    public static final String DATABASE_NAME = "Menu.db";
    private static final String TAG = "MenuDatabase";


    public MenuDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: creating db");
        String query = "CREATE TABLE IF NOT EXISTS Menu(ID Integer PRIMARY KEY AUTOINCREMENT,Item_name TEXT,Cost REAL)";
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
        String query = "CREATE TABLE IF NOT EXISTS Menu(ID Integer PRIMARY KEY AUTOINCREMENT,Item_name TEXT,Cost REAL)";
        db.execSQL(query);
    }
//
//    public Cursor checkEmployeeId(String employee_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        return db.rawQuery("SELECT * FROM Users WHERE Employee_code=?", new String[]{employee_id});
//
//    }

    public boolean insertItem(String Item_name, double Cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues new_content = new ContentValues();
        Log.d(TAG, "insertItem: writing to new content");
        new_content.put("Item_name", Item_name);
        new_content.put("Cost", Cost);
        Log.d(TAG, "insertItem: inseting to db");
        long result = db.insert("Menu", null, new_content);

        return result != -1;
    }

    public List<menuItem> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Menu", null);

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
        Cursor cur = db.rawQuery("SELECT * FROM Menu", null);
        cur.moveToFirst();
//        Log.d(TAG, "getItem:toloooooooooooo      "+ cur.getInt(0)+"   "+cur.getString(1));
        return cur.getInt(0);
    }

    public boolean editItem(int id, String item_name, double Cost) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues newValues = new ContentValues();
        newValues.put("Cost", Cost);
        newValues.put("Item_name", item_name);

        String[] args = new String[]{String.valueOf(id)};
        long result = db.update("Menu", newValues, "ID=?", args);
        return result != -1;


    }

    public boolean deleteItem(String name) {
        int res = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        res = db.delete("Menu", "Item_name=?", new String[]{name});
        return (res != 0);
    }
}