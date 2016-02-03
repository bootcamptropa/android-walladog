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
import com.walladog.walladog.models.Product;
import com.walladog.walladog.utils.SearchObject;

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

    public List<Product> getProductList() {
        return productList;
    }

    List<Product> productList = null;


    public DogListAdapter(Context context, OnPhotoClickListener cb, List<Product> products) {
        productList=products;
        this.context = context;
        mOnPhotoClickListener = cb;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_dog, parent, false);
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
                //.load(productList.get(position).getImages().get(0).getPhoto_thumbnail_url())
                //.load(R.drawable.walladogsmall)
                .load(url)
                .placeholder(R.drawable.dogplace1)
                .transform(transformation)
                .into(holder.imageView);

        holder.dogName.setText(productList.get(position).getName());
        holder.dogLocation.setText(productList.get(position).getRace());
        holder.dogRace.setText(productList.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView dogName;
        TextView dogLocation;
        TextView dogRace;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            dogName = (TextView) itemView.findViewById(R.id.txt_nombre);
            dogRace = (TextView) itemView.findViewById(R.id.txt_genero);
            dogLocation = (TextView) itemView.findViewById(R.id.txt_location);
        }
    }

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }

    //For pagination:
    public void appendItems(List<Product> products) {
        if(products!=null){
            int count = getItemCount();
            productList.addAll(products);
            notifyItemRangeInserted(count, products.size());
        }
    }
}
