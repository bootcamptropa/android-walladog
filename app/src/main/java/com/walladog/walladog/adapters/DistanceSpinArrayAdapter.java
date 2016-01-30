package com.walladog.walladog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.walladog.walladog.utils.DistanceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadock on 30/01/16.
 *
 */

public class DistanceSpinArrayAdapter extends ArrayAdapter<DistanceItem> {

    private Context context;
    private List<DistanceItem> values;

    public DistanceSpinArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        values = new ArrayList<DistanceItem>();
        for(int i=0; i<=100; i++){
            values.add(new DistanceItem(i));
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public DistanceItem getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getDistanceName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getDistanceName());
        return label;
    }

}