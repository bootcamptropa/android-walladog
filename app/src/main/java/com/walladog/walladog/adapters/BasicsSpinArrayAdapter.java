package com.walladog.walladog.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.walladog.walladog.R;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Gender;
import com.walladog.walladog.models.Race;
import com.walladog.walladog.models.State;
import com.walladog.walladog.models.Sterile;

import java.util.List;

/**
 * Created by hadock on 30/01/16.
 *
 */

public class BasicsSpinArrayAdapter<T> extends ArrayAdapter<T> {

    private final static String TAG = BasicsSpinArrayAdapter.class.getName();

    private Context context;
    private List<T> values;

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
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(12);
        label.setAllCaps(true);
        label.setHeight(20);
        if(values.get(position) instanceof Race){
            label.setText(((Race) values.get(position)).getName());
        }else if(values.get(position) instanceof Category){
            label.setText(((Category) values.get(position)).getName());
        }else if(values.get(position) instanceof Gender){
            label.setText(((Gender) values.get(position)).getName());
        }else if(values.get(position) instanceof Sterile){
            label.setText(((Sterile) values.get(position)).getName());
        }else if(values.get(position) instanceof State){
            label.setText(((State) values.get(position)).getName());
        }else{
            Log.v(TAG,"No instance of");
        }
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.spinner_line, parent, false);
        TextView label=(TextView)row.findViewById(R.id.spin_text);
        ImageView icon=(ImageView)row.findViewById(R.id.spin_image);

        if(values.get(position) instanceof Race){
            label.setText(((Race) values.get(position)).getName());
        }else if(values.get(position) instanceof Category){
            label.setText(((Category) values.get(position)).getName());
        }else if(values.get(position) instanceof Gender){
            label.setText(((Gender) values.get(position)).getName());
        }else if(values.get(position) instanceof Sterile){
            label.setText(((Sterile) values.get(position)).getName());
        }else if(values.get(position) instanceof State){
            label.setText(((State) values.get(position)).getName());
        }else{
            Log.v(TAG,"No instance of");
        }
        return row;
    }
}