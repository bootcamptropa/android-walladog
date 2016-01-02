package com.walladog.walladog.models.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by hadock on 2/01/16.
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();
    public static final String DATABASE_NAME = "walladogapp.sqlite";
    public static final int DATABASE_VERSION = 2;
    public static final long INVALID_ID = -1;

    private static DatabaseHelper sharedInstance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sharedInstance == null) {
            sharedInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sharedInstance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        // called everytime a DB connection is opened. We activate foreing keys to have ON_CASCADE deletion
        db.execSQL("PRAGMA foreign_keys = ON");

        // if API LEVEL > 16, use this
        // db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                // upgrades for version 1->2
                Log.i(TAG, "Migrating from V1 to V2");

            case 2:
                // upgrades for version 2->3

            case 3:
                // upgrades for version 3->4
        }

    }

    // utility method to create DB
    private void createDB(SQLiteDatabase db) {
        for (String sql: DBConstants.CREATE_DATABASE) {
            db.execSQL(sql);
        }
    }

    // convenience methods to convert types Java <-->SQLite

    public static int convertBooleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static boolean convertIntToBoolean(int b) {
        return b == 0 ? false : true;
    }


    public static Long convertDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date convertLongToDate(Long dateAsLong) {
        if (dateAsLong == null) {
            return null;
        }
        return new Date(dateAsLong);
    }

    public SQLiteDatabase getDB() {
        SQLiteDatabase db;
        try {
            db = this.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = this.getReadableDatabase();
        }
        return db;
    }

}