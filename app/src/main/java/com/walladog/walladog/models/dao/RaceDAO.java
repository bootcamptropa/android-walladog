package com.walladog.walladog.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.db.DatabaseHelper;
import com.walladog.walladog.models.interfaces.DAOPersistable;

import java.lang.ref.WeakReference;

import static com.walladog.walladog.models.db.DBConstants.*;

public class RaceDAO implements DAOPersistable<Race> {

    public static final String TAG = RaceDAO.class.getName();

    public static final String[] allColumns = {
            KEY_RACES_ID,
            KEY_RACES_NAME,
            KEY_RACES_CREATION_DATE,
            KEY_RACES_MODIFICATION_DATE
    };

    private WeakReference<Context> context;

    public RaceDAO(@NonNull Context context) {
        this.context = new WeakReference<Context>(context);
    }


    @Override
    public long insert(@NonNull Race race) {
        if (race == null) {
            Log.v(TAG, "Race is null");
            return 0;
        }
        // insert
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context.get());
        SQLiteDatabase db = dbHelper.getDB();

        db.beginTransaction();
        long id = DatabaseHelper.INVALID_ID;
        try {
            //id = dbHelper.getWritableDatabase().insert(TABLE_RACES, null, this.getContentValues(race));
            id = dbHelper.getWritableDatabase().insertWithOnConflict(TABLE_RACES, null, this.getContentValues(race), SQLiteDatabase.CONFLICT_IGNORE);
            race.setId(id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        dbHelper.close();
        dbHelper=null;

        return id;
    }

    @Override
    public void update(long id, @NonNull Race race) {

        if (race == null) {
            return;
        }

        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().update(TABLE_RACES, this.getContentValues(race), KEY_RACES_ID + "=" + id, null);

        db.close();
        db=null;

    }

    @Override
    public void delete(long id) {
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().delete(TABLE_RACES, KEY_RACES_ID + " = " + id, null);

        db.close();
        db=null;
    }

    @Override
    public void delete(@NonNull Race data) {

    }

    @Override
    public void deleteAll() {
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().delete(TABLE_RACES,  null, null);

        db.close();
        db=null;
    }

    // convenience method
    public static Race  raceFromCursor(Cursor c) {
        assert c != null;

        Race n = new Race(c.getString(c.getColumnIndex(KEY_RACES_NAME)));
        n.setId(c.getInt(c.getColumnIndex(KEY_RACES_ID)));

        Long creationDate = c.getLong(c.getColumnIndex(KEY_RACES_CREATION_DATE));
        Long modificationDate = c.getLong(c.getColumnIndex(KEY_RACES_MODIFICATION_DATE));

        n.setCreationDate(DatabaseHelper.convertLongToDate(creationDate));
        n.setModificationDate(DatabaseHelper.convertLongToDate(modificationDate));

        return n;
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        //Select de toda la vida
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        Cursor c = db.getReadableDatabase().query(TABLE_RACES, allColumns, null, null, null, null, null);

        return c;
    }

    @Override
    public @Nullable Race query(long id) {
        Race race = null;

        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        String where = KEY_RACES_ID + "=" + id;
        Cursor c = db.getReadableDatabase().query(TABLE_RACES, allColumns, where, null, null, null, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                race = raceFromCursor(c);
            }
        }
        c.close();
        db.close();
        return race;
    }

    public static ContentValues getContentValues(Race race) {
        ContentValues content = new ContentValues();
        content.put(KEY_RACES_NAME, race.getName());
        //content.put(KEY_RACES_ID, notebook.getId());
        content.put(KEY_RACES_CREATION_DATE, DatabaseHelper.convertDateToLong(race.getCreationDate()));
        content.put(KEY_RACES_MODIFICATION_DATE, DatabaseHelper.convertDateToLong(race.getModificationDate()));

        return content;
    }
}