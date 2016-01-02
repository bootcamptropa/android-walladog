package com.walladog.walladog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.walladog.walladog.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Suleiman on 26-07-2015.
 *
 */

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.MasonryView> {

    private static String TAG = DogListAdapter.class.getName();
    private OnPhotoClickListener mOnPhotoClickListener=null;

    private Context context;

    List<String> nameList2 = null;

    public DogListAdapter(Context context) {
        nameList2 = new ArrayList<>();
        for(int i=1; i<1000; i++){
            nameList2.add("Numero "+String.valueOf(i));
        }
        this.context = context;
        mOnPhotoClickListener = (OnPhotoClickListener) context;
    }

    public DogListAdapter(Context context, OnPhotoClickListener cb) {
        nameList2 = new ArrayList<>();
        for(int i=1; i<1000; i++){
            nameList2.add("Numero "+String.valueOf(i));
        }
        this.context = context;
        mOnPhotoClickListener = cb;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MasonryView(layoutView);
    }



    @Override
    public void onBindViewHolder(MasonryView holder, final int position) {

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Item clicked");
                mOnPhotoClickListener.onPhotoClick(position);
            }
        });

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(5)
                .oval(false)
                .build();

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(20);
        int height = 300 + randomInt;
        String url = "http://loremflickr.com/300/"+String.valueOf(height)+"/dog";

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.huellaplaceholder)
                .transform(transformation)
                .into(holder.imageView);

        //holder.textView.setText(nameList[position]);
        holder.textView.setText(nameList2.get(position));
    }

    @Override
    public int getItemCount() {
        return nameList2.size();
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

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }
}
