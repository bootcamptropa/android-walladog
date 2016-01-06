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
public class SellingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> items;
    private SparseBooleanArray selectedItems;

    private static final int FOOTER_VIEW = 1;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;

        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcy_sell_itemfooter, viewGroup, false);
            FooterViewHolder vh = new FooterViewHolder(v);
            return vh;
        }

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rcy_sell_item, viewGroup, false);
        ListItemViewHolder vh = new ListItemViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        try {
            if (viewHolder instanceof ListItemViewHolder) {
                ListItemViewHolder vh = (ListItemViewHolder) viewHolder;
                Product model = items.get(position);
                vh.name.setText(String.valueOf(model.getName()));
                vh.publish_date.setText(String.valueOf(model.getGender()));

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
                        .into(vh.img_product);
                viewHolder.itemView.setActivated(selectedItems.get(position, false));
                vh.bindView(position);
            } else  {
                FooterViewHolder vh = (FooterViewHolder) viewHolder;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        if (items.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }
        // Add extra view to show the footer view
        return items.size() + 1;
    }

    public class ListItemViewHolder extends ViewHolder {
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

    @Override
    public int getItemViewType(int position) {
        if(position==items.size()){
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    public class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do whatever you want on clicking the item
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define elements of a row here
        public ViewHolder(View itemView) {
            super(itemView);
            // Find view by ID and initialize here
        }

        public void bindView(int position) {
            // bindView() method to implement actions
        }
    }
}