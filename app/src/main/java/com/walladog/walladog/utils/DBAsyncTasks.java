package com.walladog.walladog.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.dao.CategoryDAO;
import com.walladog.walladog.models.dao.RaceDAO;
import com.walladog.walladog.models.db.DatabaseHelper;

import java.util.List;

/**
 * Created by hadock on 6/01/16.
 *
 */

public class DBAsyncTasks<T> extends AsyncTask<Object, Object, Void> {

    private final static String TAG = DBAsyncTasks.class.getName();

    private Context context;
    private List<T> itemList;
    Class<T> type;

    public DBAsyncTasks(Context context,List<T> items) {
        this.context = context;
        this.itemList=items;
    }


    @Override
    protected Void doInBackground(Object... params) {
        DatabaseHelper.getInstance(context);

        if(type.isInstance(Race.class)){
            RaceDAO objDAO = new RaceDAO(context);
            for (T item : itemList) {
                objDAO.insert((Race) item);
            }
        } else if(type.isInstance(Category.class)){
            CategoryDAO objDAO = new CategoryDAO(context);
            for (T item : itemList) {
                objDAO.insert((Category) item);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.v(TAG, "Saved successfull into DB");
    }
}