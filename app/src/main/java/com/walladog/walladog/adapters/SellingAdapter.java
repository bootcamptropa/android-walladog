package com.walladog.walladog.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.walladog.walladog.R;
import com.walladog.walladog.controllers.fragments.DogDetailFragment;
import com.walladog.walladog.controllers.fragments.EditProductFragment;
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
    private ImageButton mProdOptions;
    Product model;
    private FragmentManager mFm;

    private Context context;

    public SellingAdapter(List<Product> modelData,Context context) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.context = context;
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    public SellingAdapter(List<Product> modelData,Context context,android.support.v4.app.FragmentManager fm) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.context = context;
        mFm = fm;
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
                model = items.get(position);
                vh.name.setText(String.valueOf(model.getName()));
                vh.publish_date.setText(String.valueOf(model.getCategory()));

                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.parseColor("#5e7974"))
                        .borderWidthDp(1)
                        .cornerRadiusDp(5)
                        .oval(false)
                        .build();

                Picasso.with(context)
                        .load(model.getImages().get(0).getPhoto_thumbnail_url())
                        .transform(transformation)
                        .placeholder(R.drawable.progress_animation2)
                        .into(vh.img_product);
                viewHolder.itemView.setActivated(selectedItems.get(position, false));


                mProdOptions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsDialog(model);
                    }
                });


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
            mProdOptions = (ImageButton) itemView.findViewById(R.id.btn_prod_options);


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

    private void optionsDialog(final Product oProduct){
        final CharSequence[] options = {"Ver","Editar","Eliminar","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Que acción quieres realizar");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which] == "Ver") {
                    mFm.beginTransaction()
                            .replace(R.id.drawer_layout_main_activity_frame, DogDetailFragment.newInstance(oProduct),DogDetailFragment.class.getName())
                            .addToBackStack(DogDetailFragment.class.getName())
                            .commit();
                } else if (options[which] == "Editar") {
                    mFm.beginTransaction()
                            .replace(R.id.drawer_layout_main_activity_frame, EditProductFragment.newInstance(oProduct),EditProductFragment.class.getName())
                            .addToBackStack(EditProductFragment.class.getName())
                            .commit();
                } else if (options[which] == "Eliminar") {
                    //Start delete
                } else {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}