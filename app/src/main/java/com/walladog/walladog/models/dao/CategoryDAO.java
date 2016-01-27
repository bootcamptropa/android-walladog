package com.walladog.walladog.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.db.DatabaseHelper;
import com.walladog.walladog.models.interfaces.DAOPersistable;

import java.lang.ref.WeakReference;

import static com.walladog.walladog.models.db.DBConstants.KEY_CATEGORIES_CREATION_DATE;
import static com.walladog.walladog.models.db.DBConstants.KEY_CATEGORIES_ID;
import static com.walladog.walladog.models.db.DBConstants.KEY_CATEGORIES_MODIFICATION_DATE;
import static com.walladog.walladog.models.db.DBConstants.KEY_CATEGORIES_NAME;
import static com.walladog.walladog.models.db.DBConstants.TABLE_CATEGORIES;

public class CategoryDAO implements DAOPersistable<Category> {

    public static final String TAG = CategoryDAO.class.getName();

    public static final String[] allColumns = {
            KEY_CATEGORIES_ID,
            KEY_CATEGORIES_NAME,
            KEY_CATEGORIES_CREATION_DATE,
            KEY_CATEGORIES_MODIFICATION_DATE
    };

    private WeakReference<Context> context;

    public CategoryDAO(@NonNull Context context) {
        this.context = new WeakReference<Context>(context);
    }


    @Override
    public long insert(@NonNull Category category) {
        if (category == null) {
            Log.v(TAG, "Category is null");
            return 0;
        }
        // insert
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context.get());
        SQLiteDatabase db = dbHelper.getDB();

        db.beginTransaction();
        long id = DatabaseHelper.INVALID_ID;
        try {
            id = dbHelper.getWritableDatabase().insertWithOnConflict(TABLE_CATEGORIES, null, this.getContentValues(category), SQLiteDatabase.CONFLICT_IGNORE);
            category.setId(id);
            db.setTransactionSuccessful();
        }catch(Exception e){
                e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        dbHelper.close();
        dbHelper=null;

        return id;
    }

    @Override
    public void update(long id, @NonNull Category category) {

        if (category == null) {
            return;
        }

        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().update(TABLE_CATEGORIES, this.getContentValues(category), KEY_CATEGORIES_ID + "=" + id, null);

        db.close();
        db=null;

    }

    @Override
    public void delete(long id) {
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().delete(TABLE_CATEGORIES, KEY_CATEGORIES_ID + " = " + id, null);

        db.close();
        db=null;
    }

    @Override
    public void delete(@NonNull Category data) {

    }

    @Override
    public void deleteAll() {
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().delete(TABLE_CATEGORIES,  null, null);

        db.close();
        db=null;
    }

    // convenience method
    public static Category  categoryFromCursor(Cursor c) {
        assert c != null;

        Category n = new Category(c.getString(c.getColumnIndex(KEY_CATEGORIES_NAME)));
        n.setId(c.getInt(c.getColumnIndex(KEY_CATEGORIES_ID)));

        Long creationDate = c.getLong(c.getColumnIndex(KEY_CATEGORIES_CREATION_DATE));
        Long modificationDate = c.getLong(c.getColumnIndex(KEY_CATEGORIES_MODIFICATION_DATE));

        n.setCreationDate(DatabaseHelper.convertLongToDate(creationDate));
        n.setModificationDate(DatabaseHelper.convertLongToDate(modificationDate));

        return n;
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        //Select de toda la vida
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());
        Cursor c = db.getReadableDatabase().query(TABLE_CATEGORIES, allColumns, null, null, null, null, null);
        return c;
    }

    @Override
    public @Nullable Category query(long id) {
        Category category = null;

        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        String where = KEY_CATEGORIES_ID + "=" + id;
        Cursor c = db.getReadableDatabase().query(TABLE_CATEGORIES, allColumns, where, null, null, null, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                category = categoryFromCursor(c);
            }
        }
        c.close();
        db.close();
        return category;
    }

    public static ContentValues getContentValues(Category category) {
        ContentValues content = new ContentValues();
        content.put(KEY_CATEGORIES_NAME, category.getName());
        //content.put(KEY_CATEGORIES_ID, notebook.getId());
        content.put(KEY_CATEGORIES_CREATION_DATE, DatabaseHelper.convertDateToLong(category.getCreationDate()));
        content.put(KEY_CATEGORIES_MODIFICATION_DATE, DatabaseHelper.convertDateToLong(category.getModificationDate()));

        return content;
    }
}