package com.walladog.walladog.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by Suleiman on 26-07-2015.
 *
 */

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {

    private Context context;

    int[] imgList = {R.drawable.sample_4, R.drawable.sample_3, R.drawable.sample_6, R.drawable.sample_5,
            R.drawable.sample_4, R.drawable.sample_3, R.drawable.sample_0, R.drawable.sample_7,
            R.drawable.sample_1, R.drawable.sample_3};

    String[] nameList = {"One", "Two", "Three", "Four", "Five", "Six",
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
/*
        Resources res = context.getResources();
        int id = imgList[position];
        Bitmap mBitmap = BitmapFactory.decodeResource(res, id);
        holder.imageView.setImageResource(imgList[position]);
*/

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(context)
                .load("http://lorempixel.com/300/300/animals")
                .placeholder(R.drawable.walladogsmall)
                .transform(transformation)
                .into(holder.imageView);

        //holder.riv.setImageBitmap(mBitmap);
        //mBitmap.recycle();
        holder.textView.setText(nameList[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        RoundedImageView riv;
        TextView textView;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            //riv = (RoundedImageView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.img_name);

        }
    }
}
