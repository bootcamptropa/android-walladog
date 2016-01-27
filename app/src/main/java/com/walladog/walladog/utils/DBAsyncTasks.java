package com.walladog.walladog.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.WDNotification;
import com.walladog.walladog.models.dao.CategoryDAO;
import com.walladog.walladog.models.dao.NotificationDAO;
import com.walladog.walladog.models.dao.RaceDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadock on 6/01/16.
 *
 */

public class DBAsyncTasks<T> extends AsyncTask<Object, Object, Void> {

    private final static String TAG = DBAsyncTasks.class.getName();

    private Context context;
    private List<T> itemList;
    private T type;
    private String mTaskType;

    private OnItemsSavedToDBListener mOnItemsSavedToDBListener=null;
    private OnItemsRecoveredFromDBListener mOnItemsRecoveredFromDBListener=null;

    public static final String TASK_SAVE_LIST = "task_save_list";
    public static final String TASK_SAVE_ITEM = "task_save_item";


    public DBAsyncTasks(String taskType, T myType, Context context, List<T> items, OnItemsSavedToDBListener listener) {
        this.type=myType;
        this.context = context;
        this.itemList=items;
        this.mOnItemsSavedToDBListener=(OnItemsSavedToDBListener) listener;
        this.mTaskType=taskType;
    }

    public DBAsyncTasks(String taskType, T myType, Context context, List<T> items, OnItemsRecoveredFromDBListener listener) {
        this.type=myType;
        this.context = context;
        this.itemList=items;
        this.mOnItemsSavedToDBListener=(OnItemsSavedToDBListener) listener;
        this.mTaskType=taskType;
    }


    @Override
    protected Void doInBackground(Object... params) {
        switch (mTaskType){
            case TASK_SAVE_LIST:
                if (type instanceof Race) {
                    Log.v(TAG, "Insertando Race");
                    RaceDAO objDAO = new RaceDAO(context);
                    for (T item : itemList) {
                        objDAO.insert((Race) item);
                    }
                } else if (type instanceof Category) {
                    Log.v(TAG, "Insertando Category");
                    CategoryDAO objDAO = new CategoryDAO(context);
                    for (T item : itemList) {
                        objDAO.insert((Category) item);
                    }
                } else if (type instanceof WDNotification) {
                    Log.v(TAG, "Insertando Notificacion");
                    NotificationDAO objDAO = new NotificationDAO(context);
                    for (T item : itemList) {
                        objDAO.insert((WDNotification) item);
                    }
                } else {
                    Log.v(TAG, "Clase no detectada en AsyncTaskDB");
                }
                break;
            case TASK_SAVE_ITEM:
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Log.v(TAG, "Saved successfull into DB");
        mOnItemsSavedToDBListener.onItemsSaved(true);
    }

    public interface OnItemsSavedToDBListener {
        void onItemsSaved(Boolean saved);
    }
    public interface OnItemsRecoveredFromDBListener {
        void onItemsRecovered(Boolean saved);
    }
}