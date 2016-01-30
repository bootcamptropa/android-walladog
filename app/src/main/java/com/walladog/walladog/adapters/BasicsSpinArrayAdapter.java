package com.walladog.walladog.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;

import java.util.List;

/**
 * Created by hadock on 30/01/16.
 *
 */

public class BasicsSpinArrayAdapter<T> extends ArrayAdapter<T> {

    private final static String TAG = BasicsSpinArrayAdapter.class.getName();

    // Your sent context
    private Context context;
    // Your custom values for the spinner (Race)
    private List<T> values;

    private Class<T> myClass;

    public BasicsSpinArrayAdapter(Context context, int textViewResourceId, List<T> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        //return super.getCount();
        return values.size();
    }

    @Override
    public T getItem(int position) {
        //return super.getItem(position);
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if(values.get(position) instanceof Race){
            label.setText(((Race) values.get(position)).getName());
        }else if(values.get(position) instanceof Category){
            label.setText(((Category) values.get(position)).getName());
        }else{
            Log.v(TAG,"No instance of");
        }
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        if(values.get(position) instanceof Race){
            label.setText(((Race) values.get(position)).getName());
        }else if(values.get(position) instanceof Category){
            label.setText(((Category) values.get(position)).getName());
        }else{
            Log.v(TAG,"No instance of");
        }


        return label;
    }
}