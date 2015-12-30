package com.walladog.walladog.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.walladog.walladog.R;

import java.util.Random;

/**
 * Created by Suleiman on 26-07-2015.
 *
 */

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {

    private static final String TAG = MasonryAdapter.class.getName();

    private Context context;

    String[] nameList = {"One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten","One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten","One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten","One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten","One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten"};

    public MasonryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        MasonryView masonryView = new MasonryView(layoutView);
        return masonryView;
    }

    @Override
    public void onBindViewHolder(MasonryView holder, int position) {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(20);
        int height = 300 + randomInt;
        String url = "http://loremflickr.com/300/"+String.valueOf(height)+"/dog";

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.walladogsmall)
                .transform(transformation)
                .into(holder.imageView);

        holder.textView.setText(nameList[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.img_name);

        }
    }
}
