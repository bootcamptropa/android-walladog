package com.walladog.walladog.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.dao.CategoryDAO;
import com.walladog.walladog.models.dao.RaceDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadock on 6/01/16.
 *
 */

public class DBAsyncTasksGet<T> extends AsyncTask<Object, Object, List<T>> {

    private final static String TAG = DBAsyncTasksGet.class.getName();

    private Context context;
    private List<T> mItemsList;
    private T mItemType;
    private String mTaskType;

    private OnItemsRecoveredFromDBListener mOnItemsRecoveredFromDBListener=null;

    public static final String TASK_GET_LIST = "task_get_list";

    public DBAsyncTasksGet(String taskType, T myType, Context context, OnItemsRecoveredFromDBListener<T> listener) {
        mItemType =myType;
        mTaskType = taskType;
        mOnItemsRecoveredFromDBListener = listener;
        this.context = context;
    }

    @Override
    protected List<T> doInBackground(Object... params) {
        switch (mTaskType) {
            case TASK_GET_LIST:
                if (mItemType instanceof Category) {
                    if (mItemType instanceof Category) {
                        CategoryDAO objDAO = new CategoryDAO(context);
                        mItemsList = new ArrayList<T>();
                        Cursor c = objDAO.queryCursor();
                        if(c.moveToFirst()){
                            do{
                                mItemsList.add((T) objDAO.categoryFromCursor(c));
                            }while (c.moveToNext());
                        }else{
                            return null;
                        }
                    }
                }
                if(mItemType instanceof Race) {
                    RaceDAO objDAO = new RaceDAO(context);
                    mItemsList = new ArrayList<T>();
                    Cursor c = objDAO.queryCursor();
                    if(c.moveToFirst()){
                        do{
                            mItemsList.add((T) objDAO.raceFromCursor(c));
                        }while (c.moveToNext());
                    }else{
                        return null;
                    }
                }
                break;
        }
        return mItemsList;
    }

    @Override
    protected void onPostExecute(List<T> ts) {
        super.onPostExecute(ts);
        mOnItemsRecoveredFromDBListener.onItemsRecovered(mItemsList);
    }

    public interface OnItemsRecoveredFromDBListener<T> {
        void onItemsRecovered(List<T> items);
    }
}