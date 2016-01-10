package com.walladog.walladog.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.walladog.walladog.models.WDNotification;
import com.walladog.walladog.models.db.DatabaseHelper;
import com.walladog.walladog.models.interfaces.DAOPersistable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_CREATION_DATE;
import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_ID;
import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_MODIFICATION_DATE;
import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_READ;
import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_TITLE;
import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_AUTHOR;
import static com.walladog.walladog.models.db.DBConstants.KEY_NOTIFICATIONS_MESSAGE;
import static com.walladog.walladog.models.db.DBConstants.TABLE_NOTIFICATIONS;

/**
 * Created by hadock on 9/01/16.
 *
 */
public class NotificationDAO implements DAOPersistable<WDNotification> {

    public static final String TAG = NotificationDAO.class.getName();

    public static final String[] allColumns = {
            KEY_NOTIFICATIONS_ID,
            KEY_NOTIFICATIONS_TITLE,
            KEY_NOTIFICATIONS_MESSAGE,
            KEY_NOTIFICATIONS_AUTHOR,
            KEY_NOTIFICATIONS_CREATION_DATE,
            KEY_NOTIFICATIONS_MODIFICATION_DATE
    };

    private WeakReference<Context> context;

    public NotificationDAO(@NonNull Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public long insert(@NonNull WDNotification data) {
        if (data == null) {
            Log.v(TAG, "Notification is null");
            return 0;
        }
        // insert
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context.get());
        SQLiteDatabase db = dbHelper.getDB();

        db.beginTransaction();
        long id = DatabaseHelper.INVALID_ID;
        try {
            id = dbHelper.getWritableDatabase()
                    .insertWithOnConflict(TABLE_NOTIFICATIONS, null, this.getContentValues(data), SQLiteDatabase.CONFLICT_IGNORE);
            data.setId(id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        dbHelper.close();
        dbHelper=null;

        return id;
    }

    @Override
    public void update(long id, @NonNull WDNotification data) {
        if (data == null) {
            return;
        }

        DatabaseHelper db = DatabaseHelper.getInstance(context.get());
        db.getWritableDatabase().update(TABLE_NOTIFICATIONS, this.getContentValues(data), KEY_NOTIFICATIONS_ID + "=" + id, null);

        db.close();
        db=null;
    }

    @Override
    public void delete(long id) {
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().delete(TABLE_NOTIFICATIONS, KEY_NOTIFICATIONS_ID + " = " + id, null);

        db.close();
        db=null;
    }



    @Override
    public void delete(@NonNull WDNotification data) {

    }

    @Override
    public void deleteAll() {
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        db.getWritableDatabase().delete(TABLE_NOTIFICATIONS,  null, null);

        db.close();
        db=null;
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        //Select de toda la vida
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());
        Cursor c = db.getReadableDatabase().query(TABLE_NOTIFICATIONS, allColumns, null, null, null, null, null);
        return c;
    }

    public List<WDNotification> getNotificationsFromType(String type) {
        String[] args = new String[] {type};
        DatabaseHelper db = DatabaseHelper.getInstance(context.get());
        Cursor c = db.getReadableDatabase().query(TABLE_NOTIFICATIONS, allColumns, "read=?", args, null, null, null);

        List<WDNotification> mResults = new ArrayList<WDNotification>();
        if(c.moveToFirst()){
            do {
                WDNotification myNoti = notificationFromCursor(c);
                mResults.add(myNoti);
            } while(c.moveToNext());
        }else{
            Log.v(TAG,"No results");
        }
        return mResults;
    }

    @Override
    public WDNotification query(long id) {
        WDNotification race = null;

        DatabaseHelper db = DatabaseHelper.getInstance(context.get());

        String where = KEY_NOTIFICATIONS_ID + "=" + id;
        Cursor c = db.getReadableDatabase().query(TABLE_NOTIFICATIONS, allColumns, where, null, null, null, null);
        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                race = notificationFromCursor(c);
            }
        }
        if(c!=null)
            c.close();
        db.close();
        return race;
    }



    // convenience method
    public static WDNotification  notificationFromCursor(Cursor c) {
        assert c != null;

        WDNotification n = new WDNotification(c.getString(c.getColumnIndex(KEY_NOTIFICATIONS_TITLE)),c.getString(c.getColumnIndex(KEY_NOTIFICATIONS_MESSAGE)),c.getString(c.getColumnIndex(KEY_NOTIFICATIONS_AUTHOR)));
        n.setId(c.getInt(c.getColumnIndex(KEY_NOTIFICATIONS_ID)));

        Long creationDate = c.getLong(c.getColumnIndex(KEY_NOTIFICATIONS_CREATION_DATE));
        Long modificationDate = c.getLong(c.getColumnIndex(KEY_NOTIFICATIONS_MODIFICATION_DATE));

        n.setCreationDate(DatabaseHelper.convertLongToDate(creationDate));
        n.setModificationDate(DatabaseHelper.convertLongToDate(modificationDate));

        return n;
    }

    public static ContentValues getContentValues(WDNotification notification) {
        ContentValues content = new ContentValues();
        content.put(KEY_NOTIFICATIONS_TITLE, notification.getTitle());
        content.put(KEY_NOTIFICATIONS_MESSAGE, notification.getMessage());
        content.put(KEY_NOTIFICATIONS_AUTHOR, notification.getAuthor());
        content.put(KEY_NOTIFICATIONS_READ, notification.isRead());
        //content.put(KEY_NOTIFICATIONS_ID, notebook.getId());
        content.put(KEY_NOTIFICATIONS_CREATION_DATE, DatabaseHelper.convertDateToLong(notification.getCreationDate()));
        content.put(KEY_NOTIFICATIONS_MODIFICATION_DATE, DatabaseHelper.convertDateToLong(notification.getModificationDate()));

        return content;
    }

}

