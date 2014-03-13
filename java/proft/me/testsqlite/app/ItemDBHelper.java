package proft.me.testsqlite.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class ItemDBHelper extends SQLiteOpenHelper {
    public ItemDBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table "+
                Constants.TABLE_NAME+" ("+
                Constants.KEY_ID+" integer primary key autoincrement, "+
                Constants.TITLE_NAME+" text not null);";
        try {
            db.execSQL(CREATE_TABLE);
        } catch(SQLiteException ex) {
            Log.v(Constants.LOG_TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertItem(Item item) {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.TITLE_NAME, item.getTitle());
        try {
            result = db.insert(Constants.TABLE_NAME, null, cv);
            db.close();
        } catch(SQLiteException ex) {
            result = -1;
        }
        return result;
    }

    public long updateItem(Item item) {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.TITLE_NAME, item.getTitle());
        result = db.update(Constants.TABLE_NAME, cv, Constants.KEY_ID + " = ?", new String[] { String.valueOf(item.getID()) });
        db.close();
        return result;
    }

    public long deleteItem(Item item) {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ", new String[] { String.valueOf(item.getID()) });
        return result;
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, Constants.COLUMNS, Constants.KEY_ID + " = ? ",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Item item = new Item(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return item;
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<Item>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setTitle(cursor.getString(1));
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    public int getCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

}