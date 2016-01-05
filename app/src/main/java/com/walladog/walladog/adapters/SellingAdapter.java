package com.walladog.walladog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.walladog.walladog.R;
import com.walladog.walladog.models.Product;

import java.util.List;
import java.util.Random;

/**
 * Created by hadock on 5/01/16.
 *
 */
public class SellingAdapter extends RecyclerView.Adapter<SellingAdapter.ListItemViewHolder> {

    private List<Product> items;
    private SparseBooleanArray selectedItems;

    private Context context;

    public SellingAdapter(List<Product> modelData,Context context) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.context = context;
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.rcy_sell_item, viewGroup, false);

        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        Product model = items.get(position);
        viewHolder.name.setText(String.valueOf(model.getName()));
        viewHolder.publish_date.setText(String.valueOf(model.getGender()));

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.parseColor("#5e7974"))
                .borderWidthDp(5)
                .cornerRadiusDp(10)
                .oval(false)
                .build();

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(20);
        int height = 300 + randomInt;
        String url = "http://loremflickr.com/300/"+String.valueOf(height)+"/dog";
        Picasso.with(context)
                .load(url)
                .transform(transformation)
                .placeholder(R.drawable.progress_animation2)
                .into(viewHolder.img_product);
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView publish_date;
        ImageView img_product;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            publish_date = (TextView) itemView.findViewById(R.id.txt_publishdate);
            img_product = (ImageView) itemView.findViewById(R.id.img_grid);
        }
    }
}